package ch.fortylove.util;

import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@SpringComponent
public class DateTimeUtil {

    @Nonnull
    public LocalDate today() {
        return LocalDate.now();
    }

    @Nonnull
    public LocalTime now() {
        ZoneId zoneId = ZoneId.of("Europe/Berlin");
        return LocalTime.now(zoneId);
    }

    @Nonnull
    public LocalDateTime todayNow() {
        return LocalDateTime.of(today(), now());
    }
}
