package ch.fortylove.persistence.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "users")
public class User extends AbstractEntity implements HasIdentifier, Comparable<User>{

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @Email
    @NotBlank
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authenticationdetails_id")
    private AuthenticationDetails authenticationDetails;

    @NotNull
    @Column(name = "userstatus")
    private UserStatus userStatus;

    @Column(name = "enabled")
    private boolean enabled;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Booking> ownerBookings = new HashSet<>();

    @ManyToMany(mappedBy = "opponents", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Booking> opponentBookings = new HashSet<>();

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playerstatus_id")
    private PlayerStatus playerStatus;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usersettings_id")
    private UserSettings userSettings;

    protected User() {
    }

    public User(@Nonnull final String firstName,
                @Nonnull final String lastName,
                @Nonnull final String email,
                @Nonnull final AuthenticationDetails authenticationDetails,
                @Nonnull final UserStatus userStatus,
                final boolean enabled,
                @Nonnull final Set<Role> roles,
                @Nonnull final PlayerStatus playerStatus,
                @Nonnull final UserSettings userSettings) {
        super(UUID.randomUUID());
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.authenticationDetails = authenticationDetails;
        authenticationDetails.setUser(this);
        this.userStatus = userStatus;
        this.enabled = enabled;
        this.roles = roles;
        this.playerStatus = playerStatus;
        this.userSettings = userSettings;
        userSettings.setUser(this);
    }

    @Nonnull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Nonnull final String firstName) {
        this.firstName = firstName;
    }

    @Nonnull
    public AuthenticationDetails getAuthenticationDetails() {
        return authenticationDetails;
    }

    public void setAuthenticationDetails(@Nonnull final AuthenticationDetails authenticationDetails) {
        this.authenticationDetails = authenticationDetails;
    }

    @Nonnull
    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(@Nonnull final UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Nonnull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@Nonnull final String lastName) {
        this.lastName = lastName;
    }

    @Nonnull
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Nonnull
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nonnull final String username) {
        this.email = username;
    }

    @Nonnull
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(@Nonnull final Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    @Nonnull
    public Set<Booking> getOwnerBookings() {
        return ownerBookings;
    }

    public void setOwnerBookings(@Nonnull final Set<Booking> ownerBookings) {
        this.ownerBookings = ownerBookings;
    }

    @Nonnull
    public Set<Booking> getOpponentBookings() {
        return opponentBookings;
    }

    public void setOpponentBookings(@Nonnull final Set<Booking> opponentBookings) {
        this.opponentBookings = opponentBookings;
    }

    @Nonnull
    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(@Nonnull final PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    @Nonnull
    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(@Nonnull final UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    @Nonnull
    public String getIdentifier() {
        return getFullName();
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", userStatus=" + userStatus +
                ", playerStatus=" + playerStatus +
                '}';
    }

    @Override
    public int compareTo(@Nonnull final User otherUser) {
        return this.lastName.compareTo(otherUser.getLastName());
    }
}