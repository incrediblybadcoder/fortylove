package ch.fortylove.view.membermanagement.events;

import ch.fortylove.view.membermanagement.UserForm;
import ch.fortylove.view.membermanagement.dto.UserFormInformations;
import com.vaadin.flow.component.ComponentEvent;

//Events
public abstract class UserFormEvent extends ComponentEvent<UserForm> {
    private final UserFormInformations user;

    protected UserFormEvent(UserForm source, UserFormInformations user) {
        super(source, false);
        this.user = user;
    }

    public UserFormInformations getUser() {
        return user;
    }
}
