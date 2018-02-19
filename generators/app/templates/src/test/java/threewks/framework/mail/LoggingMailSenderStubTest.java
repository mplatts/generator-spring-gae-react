package threewks.framework.mail;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import threewks.framework.MockLogback;
import threewks.framework.MockLogging;
import threewks.util.HtmlFormatter;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggingMailSenderStubTest {
    @Rule
    public MockLogging mockLogging = new MockLogback(LoggingMailSenderStub.class);

    @InjectMocks
    private LoggingMailSenderStub loggingMailSender;

    @Mock
    private HtmlFormatter htmlFormatter;

    private MimeMessageHelper message;

    @Before
    public void before() throws Exception {
        message = new MimeMessageHelper(new MimeMessage((Session) null), true, "UTF-8");
    }


    @Test
    public void doSend() throws MessagingException {
        message.setFrom("From Person <from@example.org>");
        message.setReplyTo("ReplyTo Person <replyTo@example.org>");
        message.setTo(new String[]{"To Person 1 <to1@example.org>", "to2@example.org"});
        message.setCc(new String[]{"Cc Person 1 <cc1@example.org>", "cc2@example.org"});
        message.setBcc(new String[]{"Bcc Person 1 <bcc1@example.org>", "bcc2@example.org"});
        message.setSubject("Email subject");
        message.setText("<html>html content</html>", true);
        message.addInline("inline1", new ClassPathResource("application.yaml"));
        message.addAttachment("attachment1", new ClassPathResource("application-local.yaml"));
        when(htmlFormatter.toPlainText("<html>html content</html>")).thenReturn("Formatted html content");

        mockLogging.reset();
        loggingMailSender.send(message.getMimeMessage());

        String output = mockLogging.getLoggedString();
        assertThat(output, containsString("from                : From Person <from@example.org>"));
        assertThat(output, containsString("replyTo             : ReplyTo Person <replyTo@example.org>"));
        assertThat(output, containsString("to                  : To Person 1 <to1@example.org>, to2@example.org"));
        assertThat(output, containsString("cc                  : Cc Person 1 <cc1@example.org>, cc2@example.org"));
        assertThat(output, containsString("bcc                 : Bcc Person 1 <bcc1@example.org>, bcc2@example.org"));
        assertThat(output, containsString("subject             : Email subject"));
        assertThat(output, containsString("attachments         : 2"));
        assertThat(output, containsString("Formatted html content"));
    }

    @Test
    public void doSend_willLogMinimalInfo_whenMinimalProvided() throws MessagingException {
        message.setFrom("from@example.org");

        mockLogging.reset();
        loggingMailSender.send(message.getMimeMessage());

        String output = mockLogging.getLoggedString();
        assertThat(output, containsString("from                : from@example.org"));
        assertThat(output, containsString("replyTo             : from@example.org"));

        assertThat(output, not(containsString("subject")));
        assertThat(output, not(containsString("to       ")));
        assertThat(output, not(containsString("cc")));
        assertThat(output, not(containsString("bcc")));
        assertThat(output, not(containsString("attachments")));
        assertThat(output, not(containsString("null")));
    }

}
