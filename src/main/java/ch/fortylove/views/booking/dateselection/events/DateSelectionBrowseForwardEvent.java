package ch.fortylove.views.booking.dateselection.events;

import ch.fortylove.views.booking.dateselection.DateSelectionComponent;

import javax.annotation.Nonnull;

public class DateSelectionBrowseForwardEvent extends DateSelectionEvent {

    public DateSelectionBrowseForwardEvent(@Nonnull final DateSelectionComponent source) {
        super(source);
    }
}
