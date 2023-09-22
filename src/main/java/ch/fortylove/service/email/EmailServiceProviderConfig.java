package ch.fortylove.service.email;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EmailServiceProviderConfig {

    @Bean
    @Primary
    public IEmailServiceProvider emailServiceProvider(@Nonnull @Value("${email.service}") final String emailProvider,
                                                      @Nonnull final ApplicationContext context) {
        final IEmailServiceProvider emailService = context.getBean(emailProvider, IEmailServiceProvider.class);
        System.out.println("Using emailService: " + emailService.getClass());
        return emailService;
    }
}

