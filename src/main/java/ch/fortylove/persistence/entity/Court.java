package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "courts")
public class Court extends AbstractEntity {

    @OneToMany(mappedBy = "court")
    private Collection<Booking> bookings;

    @Nonnull
    public Collection<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(@Nonnull final Collection<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Court court = (Court) o;
        return Objects.equals(bookings, court.bookings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookings);
    }
}
