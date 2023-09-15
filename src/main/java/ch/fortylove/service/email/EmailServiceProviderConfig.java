package ch.fortylove.service.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EmailServiceProviderConfig {

    @Bean
    @Primary
    public IEmailServiceProvider emailServiceProvider(
            @Value("${email.service}") String emailProvider,
            ApplicationContext context) {
        return context.getBean(emailProvider, IEmailServiceProvider.class);
    }
}

