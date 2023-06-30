package ch.fortylove.views.membermanagement.events;

import ch.fortylove.views.membermanagement.UserForm;
import ch.fortylove.views.membermanagement.dto.UserFormInformations;

public class UpdateEvent extends UserFormEvent {
    public UpdateEvent(UserForm source, UserFormInformations user) {
        super(source, user);
    }
}
