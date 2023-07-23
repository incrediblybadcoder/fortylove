package ch.fortylove.security;

import ch.fortylove.presentation.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SecurityConfiguration extends VaadinWebSecurity {

    @Autowired private UserDetailsService userDetailsService;

    @Override
    protected void configure(@Nonnull final HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);

        http.authenticationProvider(getAuthenticationProvider());
    }

    @Nonnull
    private DaoAuthenticationProvider getAuthenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getPasswordEncoder());

        return authProvider;
    }

    @Nonnull
    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}