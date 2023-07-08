package ch.fortylove.util;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatUtil {

    public static DateTimeFormatter getDateTextFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY);
    }
}
