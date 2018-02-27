package threewks.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import threewks.framework.mail.GaeMailSender;
import threewks.framework.mail.LoggingMailSenderStub;
import threewks.util.HtmlFormatter;


@Configuration
public class MailConfig {

    @Profile("!gae")
    @Bean
    public JavaMailSender loggingMailSender() {
        // If HtmlFormatter is needed anywhere else we can make it a component. For now leave it here ...
        return new LoggingMailSenderStub(new HtmlFormatter());
    }

    // If you define properties via spring.mail.host, etc then you do not need this bean definition. This is specifically
    // used to setup the default appengine mailer that has very low usage limitations.
    @Profile("gae")
    @Bean
    public JavaMailSender gaeMailSender() {
        return new GaeMailSender();
    }

}
