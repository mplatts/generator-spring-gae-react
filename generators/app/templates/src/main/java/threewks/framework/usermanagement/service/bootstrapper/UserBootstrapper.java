package threewks.framework.usermanagement.service.bootstrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import threewks.framework.usermanagement.Role;
import threewks.framework.usermanagement.dto.UpdateUserRequest;
import threewks.framework.usermanagement.repository.UserRepository;
import threewks.framework.usermanagement.service.UserService;
import threewks.service.bootstrap.Bootstrapper;

@Profile("local")
@Component
public class UserBootstrapper implements Bootstrapper {
    private static final Logger LOG = LoggerFactory.getLogger(UserBootstrapper.class);
    private final UserService userService;
    private final UserRepository userRepository;

    public UserBootstrapper(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    public void bootstrap() {
        UpdateUserRequest request = new UpdateUserRequest()
            .setEmail("admin@3wks.com.au")
            .setName("Admin User")
            .grantRoles(Role.ADMIN, Role.SUPER);

        if (userRepository.findAll(1).isEmpty()) {
            LOG.info("Bootstrapping users...");
            userService.create(request, "password");
        } else {
            LOG.info("Skipping user bootstrap. Users exist.");
        }

    }

}
