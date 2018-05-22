package threewks.testinfra;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.contrib.gae.objectify.ObjectifyProxy;
import org.springframework.contrib.gae.search.SearchService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import threewks.framework.usermanagement.repository.UserRepository;
import threewks.testinfra.rules.LocalServicesRule;

import static com.googlecode.objectify.ObjectifyService.ofy;

@RunWith(SpringRunner.class)
@ActiveProfiles("junit")
@ContextConfiguration(classes = TestApplicationContext.class)
@SpringBootTest
public abstract class BaseIntegrationTest {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ObjectifyProxy objectifyProxy;
    @Autowired
    protected SearchService searchService;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public LocalServicesRule localServicesRule = new LocalServicesRule();


    @SuppressWarnings("unchecked")
    protected <E> E save(E entity) {
        ofy().save().entities(entity).now();
        return entity;
    }

}
