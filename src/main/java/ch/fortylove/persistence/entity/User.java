package ch.fortylove.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "users")
public class User extends AbstractEntity {

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Email
    @NotNull
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Column(name = "encrypted_password", length = 60)
    private String encryptedPassword;

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

    protected User() {
        super();
    }

    public User(@Nonnull final String firstName,
                @Nonnull final String lastName,
                @Nonnull final String email,
                @Nonnull final String encryptedPassword,
                final boolean enabled,
                @Nonnull final Set<Role> roles,
                @Nonnull final PlayerStatus playerStatus) {
        super(UUID.randomUUID());
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.enabled = enabled;
        this.roles = roles;
        this.playerStatus = playerStatus;
    }

    @Nonnull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Nonnull final String firstName) {
        this.firstName = firstName;
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
    public String getAbbreviatedName() {
        return firstName.substring(0,1) + ". " + lastName;
    }

    @Nonnull
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nonnull final String username) {
        this.email = username;
    }

    @Nonnull
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(@Nonnull final String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
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

    public void addOpponentBooking(@Nonnull final Booking booking) {
        opponentBookings.add(booking);
        booking.getOpponents().add(this);
    }

    public void removeOpponentBooking(@Nonnull final Booking booking) {
        opponentBookings.remove(booking);
        booking.getOpponents().remove(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", playerStatus=" + playerStatus +
                '}';
    }
}