package threewks.framework.usermanagement.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import threewks.framework.usermanagement.dto.UpdateUserRequest;
import threewks.framework.usermanagement.model.User;
import threewks.framework.usermanagement.service.UserService;

import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static threewks.util.RestUtils.response;


//TODO: user invites
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = GET, path = "/me")
    public User user(Principal principal) {
        return response(userService.get(principal.getName()));
    }

    @RequestMapping(method = GET, path = "/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public User user(@PathVariable("userId") String userId) {
        return response(userService.getById(userId));
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
