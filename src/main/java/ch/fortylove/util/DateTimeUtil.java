package ch.fortylove.util;

import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringComponent
public class DateTimeUtil {

    @Nonnull
    public LocalDate today() {
        return LocalDate.now();
    }

    @Nonnull
    public LocalTime now() {
        return LocalTime.now();
    }

    @Nonnull
    public LocalDateTime todayNow() {
        return LocalDateTime.of(today(), now());
    }
}
