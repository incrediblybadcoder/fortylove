package ch.fortylove.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Nonnull;

@Configuration
public class PasswordEncoderConfiguration {

    @Bean
    @Nonnull
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
