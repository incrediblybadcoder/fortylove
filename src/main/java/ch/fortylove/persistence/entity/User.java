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

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

@Entity(name = "users")
public class User extends AbstractEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", length = 60)
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> ownerBookings;

    @ManyToMany(mappedBy = "opponents", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Booking> opponentBookings;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_status_id")
    private PlayerStatus playerStatus;

    public User() {
        super();
    }

    public User(final long id,
                final String firstName,
                final String lastName,
                final String email,
                final String password,
                final boolean enabled,
                final List<Role> roles,
                final List<Booking> ownerBookings,
                final List<Booking> opponentBookings,
                final PlayerStatus playerStatus) {
        super(id, 0);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
        this.ownerBookings = ownerBookings;
        this.opponentBookings = opponentBookings;
        this.playerStatus = playerStatus;
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

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(final List<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public List<Booking> getOwnerBookings() {
        return ownerBookings;
    }

    public void setOwnerBookings(final List<Booking> ownerBookings) {
        this.ownerBookings = ownerBookings;
    }

    public List<Booking> getOpponentBookings() {
        return opponentBookings;
    }

    public void setOpponentBookings(final List<Booking> opponentBookings) {
        this.opponentBookings = opponentBookings;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(final PlayerStatus playerStatus) {
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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return enabled == user.enabled &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, password, enabled);
    }
}