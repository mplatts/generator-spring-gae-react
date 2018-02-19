package threewks.testinfra.rules;

import org.junit.rules.ExternalResource;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import threewks.framework.usermanagement.model.UserDetails;

import static org.mockito.Mockito.when;

public class SetUpSecurityContextRule extends ExternalResource {
    private UserDetails loggedIn;

    public SetUpSecurityContextRule(UserDetails loggedIn) {
        this.loggedIn = loggedIn;
    }

    protected void before() throws Throwable {
        initializeSecurityContext(loggedIn);
    }

    protected void after() {
    }

    private void initializeSecurityContext(UserDetails userDetails) {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

}
