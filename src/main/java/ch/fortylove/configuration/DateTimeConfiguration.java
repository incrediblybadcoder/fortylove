package ch.fortylove.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class DateTimeConfiguration {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        Locale.setDefault(Locale.GERMANY);
    }
}
