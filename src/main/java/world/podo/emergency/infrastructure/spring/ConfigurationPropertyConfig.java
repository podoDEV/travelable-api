package world.podo.emergency.infrastructure.spring;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import world.podo.emergency.infrastructure.public_api.PublicApiConfig;

@Configuration
@EnableConfigurationProperties({
        PublicApiConfig.class
})
public class ConfigurationPropertyConfig {
}
