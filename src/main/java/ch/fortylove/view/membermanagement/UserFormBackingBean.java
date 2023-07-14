package ch.fortylove.view.membermanagement;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.view.membermanagement.dto.UserFormInformations;
import com.vaadin.flow.component.combobox.ComboBox;
import jakarta.annotation.Nonnull;

public class UserFormBackingBean {

    private String firstName;
    private String lastName;
    private String email;

    @Nonnull private final ComboBox<PlayerStatus> status;

    private Long id;
    public UserFormBackingBean(final ComboBox<PlayerStatus> status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public PlayerStatus getStatus() {
        return status.getValue();
    }

    public void setStatus(@Nonnull final PlayerStatus status) {
        this.status.setValue(status);
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public UserFormInformations toUserFormInformations() {
        return new UserFormInformations(firstName, lastName, email, id, getStatus());
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
