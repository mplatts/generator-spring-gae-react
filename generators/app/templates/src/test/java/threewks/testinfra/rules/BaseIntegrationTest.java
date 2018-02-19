package threewks.testinfra.rules;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import threewks.framework.usermanagement.model.LoginIdentifier;
import threewks.framework.usermanagement.model.User;
import threewks.framework.usermanagement.model.UserDetails;
import threewks.testinfra.TestApplicationContext;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@TestPropertySource(properties = { "app.host=http://testinghost:8080" })
@ContextConfiguration(classes = TestApplicationContext.class)
@SpringBootTest
public abstract class BaseIntegrationTest {
    protected User loggedIn = User.byEmail("admin@3wks.com.au", "myPass");

    @Rule
    public SetUpSecurityContextRule setUpSecurityContextRule =
        new SetUpSecurityContextRule(new UserDetails(loggedIn.getId(), "andres", "passw", Arrays.asList()));


    @Rule
    public LocalServicesRule localServicesRule = new LocalServicesRule(User.class, LoginIdentifier.class);

}
