package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity(name = "courts")
public class Court extends AbstractEntity {

    @OneToMany(
            mappedBy = "court",
            fetch = FetchType.EAGER
    )
    private List<Booking> bookings;

    public Court() {
        super();
        bookings = new ArrayList<>();
    }

    @Nonnull
    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }

    public void setBookings(@Nonnull final List<Booking> bookings) {
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
