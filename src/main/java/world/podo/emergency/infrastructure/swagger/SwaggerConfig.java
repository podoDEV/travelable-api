package world.podo.emergency.infrastructure.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ConditionalOnWebApplication
@Configuration
public class SwaggerConfig {
}
