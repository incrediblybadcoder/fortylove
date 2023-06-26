package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity(name = "bookings")
public class BookingEntity extends AbstractEntity implements Comparable<BookingEntity>{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "court_id")
    private CourtEntity court;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "bookings_users",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> users;

    private int timeSlotIndex;

    private LocalDate date;

    public int getTimeSlotIndex() {
        return timeSlotIndex;
    }

    public void setTimeSlotIndex(final int timeSlotIndex) {
        this.timeSlotIndex = timeSlotIndex;
    }

    @Nonnull
    public CourtEntity getCourt() {
        return court;
    }

    public void setCourt(@Nonnull final CourtEntity court) {
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
    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(@Nonnull final List<UserEntity> users) {
        this.users = users;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BookingEntity booking = (BookingEntity) o;
        return timeSlotIndex == booking.timeSlotIndex &&
                Objects.equals(court, booking.court) &&
                Objects.equals(users, booking.users) &&
                Objects.equals(date, booking.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(court, users, timeSlotIndex, date);
    }

    @Override
    public int compareTo(@Nonnull final BookingEntity otherBooking) {
        return date.compareTo(otherBooking.getDate());
    }
}