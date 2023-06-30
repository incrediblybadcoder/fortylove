package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "bookings_opponents",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "user_opponent_id")
    )
    private List<User> opponents;

    private int timeSlotIndex;

    private LocalDate date;

    public Booking() {
        super();
    }

    public Booking(final long id,
                   final Court court,
                   final User owner,
                   final List<User> opponents,
                   final int timeSlotIndex,
                   final LocalDate date) {
        super(id, 0);
        this.court = court;
        this.owner = owner;
        this.opponents = opponents;
        this.timeSlotIndex = timeSlotIndex;
        this.date = date;
    }

    public int getTimeSlotIndex() {
        return timeSlotIndex;
    }

    public void setTimeSlotIndex(final int timeSlotIndex) {
        this.timeSlotIndex = timeSlotIndex;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt( final Court court) {
        this.court = court;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(final User owner) {
        this.owner = owner;
    }

    public List<User> getOpponents() {
        return opponents;
    }

    public void setOpponents(final List<User> opponents) {
        this.opponents = opponents;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Booking booking = (Booking) o;
        return timeSlotIndex == booking.timeSlotIndex &&
                Objects.equals(court, booking.court) &&
                Objects.equals(owner, booking.owner) &&
                Objects.equals(opponents, booking.opponents) &&
                Objects.equals(date, booking.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(court, owner, opponents, timeSlotIndex, date);
    }

    @Override
    public int compareTo( final Booking otherBooking) {
        return date.compareTo(otherBooking.getDate());
    }
}