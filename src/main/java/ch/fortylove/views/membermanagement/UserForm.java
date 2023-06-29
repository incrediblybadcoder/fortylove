package ch.fortylove.views.membermanagement;

import ch.fortylove.persistence.backingbeans.UserFormBackingBean;
import ch.fortylove.persistence.dto.User;
import ch.fortylove.persistence.dto.UserFormInformations;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class UserForm extends FormLayout {
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField email = new TextField("Email");
    //ToDo: add role selection

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<UserFormBackingBean> binder = new BeanValidationBinder<>(UserFormBackingBean.class); //BeanValidationBider oder nur Binder?

    public UserForm() {
        addClassName("user-form");

        binder.bindInstanceFields(this);
        //Todo: add role selection
        //Todo: add user state


        add(    firstName,
                lastName,
                email,
                createButtonsLayout());
    }

    public void setUser(User user) {
        if (user != null) {
            UserFormBackingBean backingBean = new UserFormBackingBean();
            backingBean.setFirstName(user.getFirstName());
            backingBean.setLastName(user.getLastName());
            backingBean.setEmail(user.getEmail());
            binder.setBean(backingBean);
        }
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean().toUserFormInfromations())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if(binder.isValid()){
            fireEvent(new SaveEvent(this, binder.getBean().toUserFormInfromations()));
        }
    }

    //Events
    public static abstract class UserFormEvent extends ComponentEvent<UserForm> {
        private final UserFormInformations user;

        protected UserFormEvent(UserForm source, UserFormInformations user) {
            super(source, false);
            this.user = user;
        }

        public UserFormInformations getUser() {
            return user;
        }
    }

        public static class SaveEvent extends UserFormEvent {
            SaveEvent(UserForm source, UserFormInformations user) {
                super(source, user);
            }
        }

        public static class DeleteEvent extends UserFormEvent {
            DeleteEvent(UserForm source, UserFormInformations user) {
                super(source, user);
            }
        }

        public static class CloseEvent extends UserFormEvent {
            CloseEvent(UserForm source) {
                super(source, null);
            }
        }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}

