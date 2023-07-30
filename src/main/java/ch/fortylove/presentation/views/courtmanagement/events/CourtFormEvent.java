package ch.fortylove.presentation.views.courtmanagement.events;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.presentation.views.courtmanagement.CourtForm;
import com.vaadin.flow.component.ComponentEvent;

public abstract class CourtFormEvent extends ComponentEvent<CourtForm> {
    private final Court court;

    protected CourtFormEvent(CourtForm source, Court court) {
        super(source, false);
        this.court = court;
    }

    public Court getCourt() {
        return court;
    }
}
