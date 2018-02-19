package threewks.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.contrib.gae.objectify.config.ObjectifyConfigurer;
import org.springframework.contrib.gae.objectify.config.ObjectifyEntityScanner;
import threewks.framework.usermanagement.model.LoginIdentifier;
import threewks.framework.usermanagement.model.User;

import java.util.Collection;

@Configuration
public class ObjectifyConfig implements ObjectifyConfigurer {

    @Override
    public Collection<Class<?>> registerObjectifyEntities() {
        return new ObjectifyEntityScanner("threewks.model")
            .withAdditionalClasses(User.class)
            .withAdditionalClasses(LoginIdentifier.class)
            .getEntityClasses();
    }
}
