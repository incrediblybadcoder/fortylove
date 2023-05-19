package ch.fortylove.spring;

import ch.fortylove.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Nonnull;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Nonnull private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(@Nonnull final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(@Nonnull final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequests ->
                authorizeHttpRequests.requestMatchers("/images/*.png").permitAll()
        );
        super.configure(http);
        setLoginView(http, LoginView.class);

        http.authenticationProvider(getAuthenticationProvider());
    }

    @Bean
    @Nonnull
    public DaoAuthenticationProvider getAuthenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getPasswordEncoder());

        return authProvider;
    }

    @Bean
    @Nonnull
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}