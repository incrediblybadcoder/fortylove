package ch.fortylove.util;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatUtil {

    @Nonnull
    private static DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY);
    }

    @Nonnull
    private static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy, hh:mm", Locale.GERMANY);
    }

    @Nonnull
    public static String format(@Nonnull final LocalDate date) {
        return date.format(getDateFormatter());
    }

    @Nonnull
    public static String format(@Nonnull final LocalDateTime dateTime) {
        return dateTime.format(getDateTimeFormatter());
    }
}
