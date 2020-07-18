package world.podo.travelable.infrastructure.spring;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import world.podo.travelable.infrastructure.public_api.PublicApiConfig;

@Configuration
@EnableConfigurationProperties({
        PublicApiConfig.class
})
public class ConfigurationPropertyConfig {
}
