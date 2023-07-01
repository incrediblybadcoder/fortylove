package ch.fortylove.views.membermanagement;

import ch.fortylove.views.membermanagement.dto.UserFormInformations;

public class UserFormBackingBean {

    private String firstName;
    private String lastName;
    private String email;
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
