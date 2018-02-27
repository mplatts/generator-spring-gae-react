package threewks.framework.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    private TemplateEngine templateEngine;
    private JavaMailSender mailSender;
    private String fromAddress;

    public EmailService(TemplateEngine templateEngine, JavaMailSender mailSender, @Value("${mailSender.fromAddress:myapp@email.org}") String fromAddress) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
    }

    public void send(String type, String to, Map<String, String> params) {
        EmailBuilder emailBuilder = EmailBuilder.email(to, params.get("subject"), type, params);
        send(emailBuilder.createEmail());
    }

    public void send(Email email) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, email.isMultipart());

            Context context = new Context();
            context.setVariables(email.getTemplateParams());

            String html = templateEngine.process(email.getTemplate(), context);

            helper.setTo(email.getTo());
            helper.setText(html, true);
            helper.setSubject(email.getSubject());
            helper.setFrom(fromAddress);

            for (MailAttachment attachment : email.getAttachments()) {
                helper.addAttachment(attachment.getName(), attachment.getInputStreamSource());
            }

            LOG.info("Sending email to: " + email.getTo());
            mailSender.send(message);
            LOG.info("Email sent");
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email", e);
        }
    }
}
