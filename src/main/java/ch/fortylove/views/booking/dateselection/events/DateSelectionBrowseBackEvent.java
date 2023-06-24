package ch.fortylove.views.booking.dateselection.events;

import ch.fortylove.views.booking.dateselection.DateSelectionComponent;

import javax.annotation.Nonnull;

public class DateSelectionBrowseBackEvent extends DateSelectionEvent {

    public DateSelectionBrowseBackEvent(@Nonnull final DateSelectionComponent source) {
        super(source);
    }
}
