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
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "password", length = 60)
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> ownerBookings = new ArrayList<>();

    @ManyToMany(mappedBy = "opponents", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Booking> opponentBookings = new ArrayList<>();

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_status_id")
    private PlayerStatus playerStatus;

    protected User() {
        super();
    }

    public User(@Nonnull final String firstName,
                @Nonnull final String lastName,
                @Nonnull final String email,
                @Nonnull final String password,
                final boolean enabled,
                @Nonnull final List<Role> roles,
                @Nonnull final PlayerStatus playerStatus) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nonnull final String username) {
        this.email = username;
    }

    @Nonnull
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nonnull final String password) {
        this.password = password;
    }

    @Nonnull
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(@Nonnull final List<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    @Nonnull
    public List<Booking> getOwnerBookings() {
        return ownerBookings;
    }

    public void setOwnerBookings(@Nonnull final List<Booking> ownerBookings) {
        this.ownerBookings = ownerBookings;
    }

    @Nonnull
    public List<Booking> getOpponentBookings() {
        return opponentBookings;
    }

    public void setOpponentBookings(@Nonnull final List<Booking> opponentBookings) {
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
}