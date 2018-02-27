package threewks.framework.service.email;

import java.util.List;
import java.util.Map;

public class Email {
    private String to;
    private String subject;
    private String template;
    private Map<String, String> templateParams;
    private List<MailAttachment> attachments;

    Email(String to, String subject, String template, Map<String, String> templateParams, List<MailAttachment> attachments) {
        this.to = to;
        this.subject = subject;
        this.template = template;
        this.templateParams = templateParams;
        this.attachments = attachments;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getTemplate() {
        return template;
    }

    public Map<String, String> getTemplateParams() {
        return templateParams;
    }

    public List<MailAttachment> getAttachments() {
        return attachments;
    }

    public boolean isMultipart() {
        return !attachments.isEmpty();
    }
}
