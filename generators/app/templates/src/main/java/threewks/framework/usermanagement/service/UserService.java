package threewks.framework.usermanagement.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import threewks.framework.usermanagement.dto.UpdateUserRequest;
import threewks.framework.usermanagement.model.LoginIdentifier;
import threewks.framework.usermanagement.model.User;
import threewks.framework.usermanagement.repository.LoginIdentifierRepository;
import threewks.framework.usermanagement.repository.UserRepository;
import threewks.util.Assert;

import java.util.List;
import java.util.Optional;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoginIdentifierRepository loginIdentifierRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, LoginIdentifierRepository loginIdentifierRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.loginIdentifierRepository = loginIdentifierRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public User update(final String userId, final UpdateUserRequest request) {
        return ofy().transact(() -> {
            User user = userRepository.getById(userId);
            String normalisedEmail = request.getEmail().toLowerCase();
            if (!StringUtils.equals(user.getEmail(), normalisedEmail)) {
                checkAvailability(normalisedEmail);
                loginIdentifierRepository.delete(user.getEmail());
                user.setEmail(normalisedEmail);
                loginIdentifierRepository.save(new LoginIdentifier(user));
            }

            user.setRoles(request.getRoles());
            user.setName(request.getName());

            return userRepository.save(user);
        });
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Create a placeholder user that will be activated at a later point. They intentionally are lacking roles, name etc.
     * These details will be completed at the point of activation.
     */
    public User createInvited(String email) {
        return this.create(User.invitedByEmail(email));
    }

    public User create(UpdateUserRequest request, String password) {
        User user = User.byEmail(request.getEmail(), passwordEncoder.encode(password))
            .setName(request.getName())
            .setRoles(request.getRoles());

        return create(user);
    }

    private User create(User user) {
        return ofy().transact(() -> {
            String normalisedEmail = user.getEmail().toLowerCase();
            checkAvailability(normalisedEmail);
            loginIdentifierRepository.save(new LoginIdentifier(user));
            return userRepository.save(user);
        });
    }

    public Optional<User> get(String email) {
        return loginIdentifierRepository.findById(email)
            .map(LoginIdentifier::getUser);
    }

    public Optional<User> getById(String userId) {
        return userRepository.findById(userId);
    }

    private void checkAvailability(String loginIdentifier) {
        Assert.notNull(loginIdentifier, "Availability check failed. Login identifier is required");

        if (loginIdentifierRepository.findById(loginIdentifier.toLowerCase()).isPresent()) {
            throw new LoginIdentifierUnavailableException(loginIdentifier);
        }
    }


}
