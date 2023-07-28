package ch.fortylove.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;

import javax.annotation.Nonnull;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@Profile("develop")
public class DevSecurityConfiguration extends SecurityConfiguration {

    @Override
    protected void configure(@Nonnull final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.requestMatchers(toH2Console()).permitAll())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(httpSecurityCsrfConfigurer ->
                        httpSecurityCsrfConfigurer.ignoringRequestMatchers(toH2Console()));

        super.configure(http);
    }
}