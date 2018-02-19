package threewks.framework.mail;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import threewks.util.HtmlFormatter;

import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;
import static org.springframework.contrib.gae.util.Nulls.ifNotNull;

public class LoggingMailSenderStub extends JavaMailSenderImpl {
    private static final Logger LOG = LoggerFactory.getLogger(LoggingMailSenderStub.class);

    private final HtmlFormatter htmlFormatter;

    private List<MimeMessageParser> msgs = new ArrayList<>();

    public LoggingMailSenderStub(HtmlFormatter htmlFormatter) {
        this.htmlFormatter = htmlFormatter;
    }

    @Override
    protected void doSend(MimeMessage[] mimeMessages, Object[] originalMessages) throws MailException {
        // Don't send :)
        for (MimeMessage mimeMessage : mimeMessages) {
            MimeMessageParser parser = new MimeMessageParser(mimeMessage);
            msgs.add(parser);
            try {
                parser.parse();
                String bodyString = parser.hasHtmlContent() ?
                    ifNotNull(parser.getHtmlContent(), htmlFormatter::toPlainText) :
                    parser.getPlainContent();

                StringBuilder sb = new StringBuilder();
                addProperty(sb, "from", toString(mimeMessage.getFrom()));
                addProperty(sb, "replyTo", toString(mimeMessage.getReplyTo()));
                addProperty(sb, "to", toString(parser.getTo()));
                addProperty(sb, "cc", toString(parser.getCc()));
                addProperty(sb, "bcc", toString(parser.getBcc()));
                addProperty(sb, "subject", parser.getSubject());
                addProperty(sb, "attachments", parser.getAttachmentList().isEmpty() ? null : parser.getAttachmentList().size());
                sb.append(StringUtils.defaultString(bodyString, "")).append("\n\n");

                LOG.info("Email: \n\n{}", sb.toString());
            } catch (Exception e) {
                LOG.warn("Cannot log email content", e);
            }
        }
    }

    private String toString(Address... addresses) {
        return toString(Arrays.asList(addresses));
    }

    private String toString(List<Address> addresses) {
        return addresses.stream()
            .map(Address::toString)
            .collect(joining(", "));
    }

    private void addProperty(StringBuilder sb, String key, Object obj) {
        String value = Objects.toString(obj, null);
        if (StringUtils.isNotBlank(value)) {
            sb
                .append("   ")
                .append(StringUtils.rightPad(key, 20))
                .append(": ")
                .append(value)
                .append("\n");
        }
    }

    public List<MimeMessageParser> getMsgs() {
        return msgs;
    }

    public void clear() {
        msgs = new ArrayList<>();
    }
}
