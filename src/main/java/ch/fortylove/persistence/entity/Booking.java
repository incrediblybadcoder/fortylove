package ch.fortylove.persistence.entity;

import ch.fortylove.util.FormatUtil;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "bookings")
public class Booking extends AbstractEntity implements HasIdentifier {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "court_id")
    private Court court;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_owner_id")
    private User owner;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "bookings_opponents", joinColumns = @JoinColumn(name = "booking_id"), inverseJoinColumns = @JoinColumn(name = "user_opponent_id"))
    private Set<User> opponents = new HashSet<>();

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "timeslot_id")
    private Timeslot timeslot;

    @NotNull
    @Column(name = "booking_date")
    private LocalDate date;

    protected Booking() {
    }

    public Booking(@Nonnull final Court court,
                   @Nonnull final User owner,
                   @Nonnull final Set<User> opponents,
                   @Nonnull final Timeslot timeslot,
                   @Nonnull final LocalDate date) {
        super(UUID.randomUUID());
        this.court = court;
        this.owner = owner;
        this.opponents = opponents;
        this.timeslot = timeslot;
        this.date = date;
    }

    @Nonnull
    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(@Nonnull final Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    @Nonnull
    public Court getCourt() {
        return court;
    }

    public void setCourt(@Nonnull final Court court) {
        this.court = court;
    }

    @Nonnull
    public LocalDate getDate() {
        return date;
    }

    public void setDate(@Nonnull final LocalDate date) {
        this.date = date;
    }

    @Nonnull
    public User getOwner() {
        return owner;
    }

    public void setOwner(@Nonnull final User owner) {
        this.owner = owner;
    }

    @Nonnull
    public Set<User> getOpponents() {
        return opponents;
    }

    public void setOpponents(@Nonnull final Set<User> opponents) {
        removeAllOpponents();
        opponents.forEach(this::addOpponent);
    }

    public void addOpponent(@Nonnull final User user) {
        opponents.add(user);
        user.getOpponentBookings().add(this);
    }

    public void removeOpponent(@Nonnull final User user) {
        opponents.remove(user);
        user.getOpponentBookings().remove(this);
    }

    public void removeAllOpponents() {
        for (final User opponent : opponents) {
            opponent.getOpponentBookings().remove(this);
        }
        opponents.clear();
    }

    @Nonnull
    public String getDateFormatted() {
        return FormatUtil.format(date);
    }

    @Nonnull
    public String getIdentifier() {
        return court.getIdentifier() + ": " + timeslot.getTimeIntervalText() + " / " + getDateFormatted();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "court=" + court +
                ", owner=" + owner +
                ", timeslot=" + timeslot +
                ", date=" + date +
                '}';
    }
}