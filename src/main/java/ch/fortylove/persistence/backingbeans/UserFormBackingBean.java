package ch.fortylove.persistence.backingbeans;

import ch.fortylove.persistence.dto.UserFormInformations;

public class UserFormBackingBean {

    private String firstName;
    private String lastName;
    private String email;

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

    public UserFormInformations toUserFormInfromations() {
        return new UserFormInformations(firstName, lastName, email);
    }
}
