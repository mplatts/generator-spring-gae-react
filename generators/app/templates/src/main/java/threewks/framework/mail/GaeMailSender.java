package threewks.framework.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

public class GaeMailSender extends JavaMailSenderImpl {
    @Override
    protected void doSend(MimeMessage[] mimeMessages, Object[] originalMessages) throws MailException {
        try {

            for(MimeMessage msg: mimeMessages) {
                Transport.send(msg);
            }

        } catch (AddressException e) {
            throw new MailSendException("Address exception:", e);
        } catch (MessagingException e) {
            throw new MailSendException("Messaging exception", e);
        }
    }
}
