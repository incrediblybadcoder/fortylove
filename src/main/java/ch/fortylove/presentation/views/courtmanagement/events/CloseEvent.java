package ch.fortylove.presentation.views.courtmanagement.events;

import ch.fortylove.presentation.views.courtmanagement.CourtForm;

public class CloseEvent extends CourtFormEvent {

    public CloseEvent(CourtForm source) {
        super(source, null);
    }
}
