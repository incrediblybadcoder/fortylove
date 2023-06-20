package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "bookings")
public class Booking extends AbstractEntity implements Comparable<Booking>{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "court_id")
    private Court court;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "bookings_users",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    private LocalDateTime dateTime;

    public Booking() {
        super();
        users = new ArrayList<>();
    }

    public Booking(@Nonnull final List<User> users) {
        this();
        this.users = users;
    }

    @Nonnull
    public Court getCourt() {
        return court;
    }

    public void setCourt(@Nonnull final Court court) {
        this.court = court;
    }

    @Nonnull
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(@Nonnull final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Nonnull
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(@Nonnull final List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Booking booking = (Booking) o;
        return Objects.equals(court, booking.court) &&
                Objects.equals(users, booking.users) &&
                Objects.equals(dateTime, booking.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(court, users, dateTime);
    }

    @Override
    public int compareTo(@Nonnull final Booking otherBooking) {
        return dateTime.compareTo(otherBooking.getDateTime());
    }

}