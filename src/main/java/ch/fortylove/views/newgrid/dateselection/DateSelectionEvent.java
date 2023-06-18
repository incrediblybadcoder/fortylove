package ch.fortylove.views.newgrid.dateselection;

import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;

public abstract class DateSelectionEvent extends ComponentEvent<DateSelectionComponent> {

    protected DateSelectionEvent(@Nonnull final DateSelectionComponent source) {
        super(source, false);
    }
}
