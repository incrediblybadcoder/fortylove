package ch.fortylove.view.membermanagement;

import ch.fortylove.view.membermanagement.dto.UserFormInformations;

import java.util.UUID;

public class UserFormBackingBean {

    private String firstName;
    private String lastName;
    private String email;
    private UUID id;

    public UserFormBackingBean() {
    }

    public String getFirstName() {
        return firstName;
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
        return new UserFormInformations(firstName, lastName, email, id);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }
}
