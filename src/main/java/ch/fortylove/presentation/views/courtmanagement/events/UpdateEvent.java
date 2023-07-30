package ch.fortylove.presentation.views.courtmanagement.events;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.presentation.views.courtmanagement.CourtForm;

public class UpdateEvent extends CourtFormEvent {

    public UpdateEvent(CourtForm source, Court court) {
        super(source, court);
    }
}
