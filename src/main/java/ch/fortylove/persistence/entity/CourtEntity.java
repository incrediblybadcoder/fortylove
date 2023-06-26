package ch.fortylove.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

@Entity(name = "courts")
public class CourtEntity extends AbstractEntity {

    @OneToMany(
            mappedBy = "court",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<BookingEntity> bookings;

    @Nonnull
    public List<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(@Nonnull final List<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CourtEntity court = (CourtEntity) o;
        return Objects.equals(bookings, court.bookings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookings);
    }
}
