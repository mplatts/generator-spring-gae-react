package threewks.framework.usermanagement.service;

import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.googlecode.objectify.Ref;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import threewks.framework.usermanagement.Role;
import threewks.framework.usermanagement.model.User;
import threewks.framework.usermanagement.model.UserAdapterGae;
import threewks.framework.usermanagement.model.UserInviteLink;
import threewks.framework.usermanagement.repository.UserInviteRepository;
import threewks.util.Assert;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Set;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Service
public class UserInviteService {

    private static final Logger LOG = LoggerFactory.getLogger(UserInviteService.class);

    public static final int MIN_PASSWORD_LENGTH = 8;

    private UserService userService;
    private UserInviteRepository userInviteRepository;
    private final PasswordEncoder passwordEncoder;
    private String inviteEmailTaskEndpoint;
    private String queueName;

    public UserInviteService(UserService userService,
                             UserInviteRepository userInviteRepository,
                             PasswordEncoder passwordEncoder,
                             @Value("${tasks.email.invite}") String inviteEmailTaskEndpoint,
                             @Value("${tasks.email.queue}") String queueName) {
        this.userService = userService;
        this.userInviteRepository = userInviteRepository;
        this.passwordEncoder = passwordEncoder;
        this.inviteEmailTaskEndpoint = inviteEmailTaskEndpoint;
        this.queueName = queueName;
    }

    public User invite(final String inviteeEmail, Set<String> requestRoles) {
        Set<Role> inviteeRoles = Role.parseSet(requestRoles);
        Optional<Ref<User>> userRef = UserAdapterGae.currentUserRef();
        final User issuer = userRef.flatMap(ref -> Optional.ofNullable(ref.get())).orElse(null);
        Assert.notNull(issuer, "Cannot issue invite. Issuer must be provided.");
        Assert.notBlank(inviteeEmail, "Cannot issue invite. Invitee email address must be provided.");
        Assert.isEmail(inviteeEmail, "Cannot issue invite. Invitee email address must be a valid email address.");

        return ofy().transact(() -> {
            LOG.info("Invite user: " + inviteeEmail);
            User user = userService.get(inviteeEmail).orElseGet(()->userService.createInvited(inviteeEmail));
            Assert.isFalse(user.isEnabled(), "Cannot issue invite. User account already activated.");

            String code = secureRandomAlphanumeric(100);
            LOG.info("Invite code generated: " + code);
            UserInviteLink invite = new UserInviteLink(code, issuer, user.getEmail(), inviteeRoles);
            userInviteRepository.save(invite);

            sendInviteEmail(inviteeEmail, code);

            return user;
        });
    }

    public User redeem(final String code, final String name, final String password) {
        Assert.notBlank(code, "Invite code is required");
        Assert.notBlank(name, "Name is required");
        Assert.notBlank(password, "Password is required");
        Assert.isTrue(password.length() >= MIN_PASSWORD_LENGTH,
            "Password must be " + MIN_PASSWORD_LENGTH + " characters or more in length");

        final String hashedCode = UserInviteLink.hash(code);

        return ofy().transact(() -> {
            LOG.info("Redeem invitation for " + code);
            UserInviteLink invite = userInviteRepository.getById(hashedCode);

            Assert.notNull(invite, "Invalid invite code");
            Assert.isFalse(invite.isRedeemed(), "Invite code has already been redeemed");
            Assert.isFalse(invite.hasExpired(), "Invite code has expired. Please ask your administrator to issue you an new one.");

            invite.setRedeemed(true);
            userInviteRepository.save(invite);

            User user = userService.get(invite.getEmail())
                .orElseThrow(()->new LoginIdentifierUnavailableException(invite.getEmail()));

            Assert.isFalse(user.isEnabled(), "Cannot redeem invite. User account already activated.");

            user.setRoles(invite.getRoles());
            user.setName(name);
            user.setPassword(passwordEncoder.encode(password));
            user.setEnabled(true);
            return userService.save(user);
        });
    }

    private void sendInviteEmail(String inviteeEmail, String code) {
        LOG.info(inviteEmailTaskEndpoint);
        LOG.info(queueName);
        LOG.info("Sending invitation email to: " + inviteeEmail);
        TaskOptions taskOptions = TaskOptions.Builder
            .withUrl(inviteEmailTaskEndpoint)
            .param("email", inviteeEmail)
            .param("code", code);

        QueueFactory.getQueue(queueName).add(ofy().getTransaction(), taskOptions);
        LOG.info("Event published!");

    }

    //Extract to a library.
    /**
     * Generate a random alphanumeric string using SecureRandom.
     *
     * @param length the length of the generated string
     * @return a random string of the given length
     * @see {@link SecureRandom}
     */
    private static String secureRandomAlphanumeric(int length) {
        char[] possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        return RandomStringUtils.random(
            length,
            0,
            possibleCharacters.length - 1,
            false,
            false,
            possibleCharacters,
            new SecureRandom());
    }

}
