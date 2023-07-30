package ch.fortylove.presentation.views.courtmanagement.events;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.presentation.views.courtmanagement.CourtForm;

public class SaveEvent extends CourtFormEvent {

    public SaveEvent(CourtForm source, Court court) {
        super(source, court);
    }
}
