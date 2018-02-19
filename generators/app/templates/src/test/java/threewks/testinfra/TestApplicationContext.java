package threewks.testinfra;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import threewks.Application;

@Configuration
@Import(Application.class)
public class TestApplicationContext {
}
