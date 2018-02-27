package threewks.framework.service.email;

import org.springframework.core.io.ByteArrayResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmailBuilder {
    private String to;
    private String subject;
    private String template;
    private Map<String, String> templateParams;
    private List<MailAttachment> attachments = new ArrayList<>();

    private EmailBuilder() {
    }

    public static EmailBuilder email(String to, String subject, String template, Map<String, String> templateParams) {
        EmailBuilder emailBuilder = new EmailBuilder();
        emailBuilder.to = to;
        emailBuilder.subject = subject;
        emailBuilder.template = template;
        emailBuilder.templateParams = templateParams;
        return emailBuilder;
    }

    public EmailBuilder addAttachment(String name, byte[] data) {
        attachments.add(new MailAttachment(name, new ByteArrayResource(data)));
        return this;
    }

    public Email createEmail() {
        return new Email(to, subject, template, templateParams, attachments);
    }
}
