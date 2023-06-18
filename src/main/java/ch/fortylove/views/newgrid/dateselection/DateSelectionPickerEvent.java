package ch.fortylove.views.newgrid.dateselection;

import javax.annotation.Nonnull;
import java.time.LocalDate;

public class DateSelectionPickerEvent extends DateSelectionEvent {

    @Nonnull private final LocalDate localDate;

    protected DateSelectionPickerEvent(@Nonnull final DateSelectionComponent source,
                                       @Nonnull final LocalDate localDate) {
        super(source);
        this.localDate = localDate;
    }

    @Nonnull
    public LocalDate getLocalDate() {
        return localDate;
    }
}
