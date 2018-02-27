package threewks.framework.usermanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import threewks.framework.service.email.EmailService;

import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/task")
public class InviteUsersEmailController {

    private static final Logger LOG = LoggerFactory.getLogger(InviteUsersEmailController.class);

    private EmailService emailService;
    private String host;

    @Autowired
    public InviteUsersEmailController(EmailService emailService, @Value("${app.host}") String host) {
        this.emailService = emailService;
        this.host = host;
    }

    @RequestMapping(method = POST, path = "/send-user-invite")
    public void sendInvitation(@RequestParam("email") String email, @RequestParam("code") String code) {
        LOG.info("Sending invitation email to user: " + email);
        HashMap<String, String> props = new HashMap<>();
        props.put("invite_link", String.format("%s/register/%s", this.host, code));
        props.put("subject", "You have been invited to the spring buskers app");
        emailService.send("invite-user", email, props);
    }
}
