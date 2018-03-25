package threewks.testinfra.rules;

import org.junit.rules.ExternalResource;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import threewks.framework.usermanagement.dto.AuthUser;
import threewks.framework.usermanagement.model.User;
import threewks.framework.usermanagement.model.UserAdapterGae;
import threewks.testinfra.TestData;

import static org.mockito.Mockito.when;

public class SecurityContextRule extends ExternalResource {
    private final AuthUser authUser;
    private final User user;

    public SecurityContextRule() {
        this(TestData.user());
    }

    public SecurityContextRule(User user) {
        this.user = user;
        this.authUser = (AuthUser) UserAdapterGae.byEmail(null).toUserDetails(user);
    }

    protected void before() {
        initialiseSecurityContext(authUser);
    }

    protected void after() {
        SecurityContextHolder.clearContext();
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public User getUser() {
        return user;
    }

    public String getUserId() {
        return user.getId();
    }

    private void initialiseSecurityContext(AuthUser authUser) {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(authUser);
    }

}
