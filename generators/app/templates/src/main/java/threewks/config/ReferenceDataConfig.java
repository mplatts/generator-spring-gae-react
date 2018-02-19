package threewks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import threewks.framework.ref.ReferenceDataConfigBuilder;
import threewks.framework.ref.ReferenceDataService;

@Configuration
public class ReferenceDataConfig {

    @Bean
    public ReferenceDataService referenceDataService() {
        threewks.framework.ref.ReferenceDataConfig config = new ReferenceDataConfigBuilder()
            // Add your reference data classes here
            .create();

        return new ReferenceDataService(config);
    }
}
