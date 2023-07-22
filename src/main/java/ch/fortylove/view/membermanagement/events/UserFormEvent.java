package ch.fortylove.view.membermanagement.events;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.view.membermanagement.UserForm;
import com.vaadin.flow.component.ComponentEvent;

//Events
public abstract class UserFormEvent extends ComponentEvent<UserForm> {
    private final User user;

    protected UserFormEvent(UserForm source, User user) {
        super(source, false);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
