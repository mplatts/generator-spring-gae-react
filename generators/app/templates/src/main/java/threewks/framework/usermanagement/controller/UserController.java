package threewks.framework.usermanagement.controller;

    import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import threewks.framework.usermanagement.dto.RedeemInvitationRequest;
import threewks.framework.usermanagement.dto.UpdateUserRequest;
import threewks.framework.usermanagement.model.User;
import threewks.framework.usermanagement.model.UserAdapterGae;
import threewks.framework.usermanagement.service.InviteUserRequest;
import threewks.framework.usermanagement.service.UserInviteService;
import threewks.framework.usermanagement.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static threewks.util.RestUtils.response;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserAdapterGae userAdapter;
    private final UserService userService;
    private final UserInviteService userInviteService;

    public UserController(UserAdapterGae userAdapter, UserService userService, UserInviteService userInviteService) {
        this.userAdapter = userAdapter;
        this.userService = userService;
        this.userInviteService = userInviteService;
    }

    @RequestMapping(method = GET, path = "/me")
    public User user(HttpServletResponse response) {
        Optional<User> currentUser = userAdapter.getCurrentUser();

        if (currentUser.isPresent()) {
            LOG.debug("Found existing authenticated user {}", currentUser.get().getEmail());
            return response(currentUser);
        }

        LOG.debug("User not authenticated");
        response.setStatus(HttpStatus.SC_NO_CONTENT);
        return null;
    }

    @RequestMapping(method = GET, path = "/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public User user(@PathVariable("userId") String userId) {
        return response(userService.getById(userId));
    }

    @RequestMapping(method = POST, path = "/invite")
    @PreAuthorize("hasRole('ADMIN')")
    public User inviteUser(@RequestBody InviteUserRequest inviteUserRequest) {
        return userInviteService.invite(inviteUserRequest.getEmail(), new HashSet<>(inviteUserRequest.getRoles()));
    }

    @RequestMapping(method = POST, path = "/invite/{inviteCode}")
    public User redeemInvitation(@PathVariable("inviteCode") String inviteCode, @RequestBody RedeemInvitationRequest request) {
        return userInviteService.redeem(inviteCode, request.getName(), request.getPassword());
    }

    @RequestMapping(method = PUT, path = "/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public User saveUser(@PathVariable("userId") String userId, @RequestBody UpdateUserRequest request) {
        return userService.update(userId, request);
    }

    @RequestMapping(method = GET, path = "")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> list() {
        return userService.list();
    }

}
