package ch.fortylove.view.membermanagement.dto;

import ch.fortylove.persistence.entity.PlayerStatus;

public class UserFormInformations {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final Long id;

    private final PlayerStatus playerStatus;

    public UserFormInformations(final String firstName,
                                final String lastName,
                                final String email,
                                final Long id,
                                final PlayerStatus playerStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.playerStatus = playerStatus;
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
    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }
}
