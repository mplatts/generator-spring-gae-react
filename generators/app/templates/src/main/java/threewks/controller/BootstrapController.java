package threewks.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public class BootstrapController {


    public BootstrapController() {

    }

//    @RequestMapping(method = RequestMethod.POST, value = "/system/bootstrap/super-user")
//    public void bootstrapSuperUser(
//        @RequestParam("email") Optional<String> email,
//        @RequestParam("password") String password) {
//
//        UpdateUserRequest request = new UpdateUserRequest()
//            .setEmail(email.orElse("admin@3wks.com.au"))
//            .setName("Super User")
//            .grantRoles(Role.SUPER, Role.ADMIN);
//
//        userService.create(request, password);
//    }
}
