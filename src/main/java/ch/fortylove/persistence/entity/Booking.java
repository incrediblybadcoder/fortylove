package ch.fortylove.persistence.entity;

import ch.fortylove.util.FormatUtil;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "bookings")
public class Booking extends AbstractEntity implements Comparable<Booking> {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "court_id")
    private Court court;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_owner_id")
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "bookings_opponents", joinColumns = @JoinColumn(name = "booking_id"), inverseJoinColumns = @JoinColumn(name = "user_opponent_id"))
    @Nonnull
    private List<User> opponents = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "timeslot_id")
    private Timeslot timeslot;

    @Column(name = "date")
    private LocalDate date;

    public Booking() {
        super();
    }

    public Booking(@Nonnull final Court court,
                   @Nonnull final User owner,
                   @Nonnull final List<User> opponents,
                   @Nonnull final Timeslot timeslot,
                   @Nonnull final LocalDate date) {
        super();
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
    public List<User> getOpponents() {
        return opponents;
    }

    public void setOpponents(@Nonnull final List<User> opponents) {
        this.opponents = opponents;
    }

    public void addOpponent(@Nonnull final User user) {
        opponents.add(user);
        user.getOpponentBookings().add(this);
    }

    public void removeOpponent(@Nonnull final User user) {
        opponents.remove(user);
        user.getOpponentBookings().remove(this);
    }

    public void removeOpponents() {
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < opponents.size(); i++) {
            opponents.get(i).removeOpponentBooking(this);
        }
        opponents.clear();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Booking booking = (Booking) o;
        return Objects.equals(timeslot, booking.timeslot) &&
                Objects.equals(court, booking.court) &&
                Objects.equals(date, booking.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(court, timeslot, date);
    }

    @Override
    public int compareTo(final Booking otherBooking) {
        return date.compareTo(otherBooking.getDate());
    }

    public String getDateFormatted() {
        return date.format(FormatUtil.getDateTextFormatter());
    }

    public String getIdentifier() {
        return court.getIdentifier() + ": " + timeslot.getTimeIntervalText() + " / " + getDateFormatted();
    }
}