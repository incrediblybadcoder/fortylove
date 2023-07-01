package ch.fortylove.views.membermanagement.dto;

public class UserFormInformations {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final Long id;

    public UserFormInformations(final String firstName,
                                final String lastName,
                                final String email, final Long id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }
}
