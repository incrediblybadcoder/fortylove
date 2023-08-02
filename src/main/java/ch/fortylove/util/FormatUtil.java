package ch.fortylove.util;

import jakarta.annotation.Nonnull;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatUtil {

    @Nonnull
    public static DateTimeFormatter getDateTextFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY);
    }
}
