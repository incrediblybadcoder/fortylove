package ch.fortylove.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Objects;

@Entity(name = "courts")
public class Court extends AbstractEntity {

    @OneToMany(
            mappedBy = "court",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Booking> bookings;

    public Court() {
        super();
    }

    public Court(final long id,
                 final List<Booking> bookings) {
        super(id, 0);
        this.bookings = bookings;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(final List<Booking> bookings) {
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
