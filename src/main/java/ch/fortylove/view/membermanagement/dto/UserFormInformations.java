package ch.fortylove.view.membermanagement.dto;

import java.util.UUID;

public class UserFormInformations {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final UUID id;

    public UserFormInformations(final String firstName,
                                final String lastName,
                                final String email,
                                final UUID id) {
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

    public UUID getId() {
        return id;
    }
}
