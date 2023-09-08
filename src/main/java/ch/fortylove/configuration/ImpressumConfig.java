package ch.fortylove.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/impressum.properties")
public class ImpressumConfig {
}
