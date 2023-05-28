package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "bookings")
public class Booking extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id")
    private Court court;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "bookings_users",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Collection<User> users;

    private int timeslot;

    @Nonnull
    public Court getCourt() {
        return court;
    }

    public void setCourt(@Nonnull final Court court) {
        this.court = court;
    }

    public int getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(final int timeslot) {
        this.timeslot = timeslot;
    }

    @Nonnull
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(@Nonnull final Collection<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Booking booking = (Booking) o;
        return timeslot == booking.timeslot &&
                Objects.equals(court, booking.court) &&
                Objects.equals(users, booking.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(court, users, timeslot);
    }
}