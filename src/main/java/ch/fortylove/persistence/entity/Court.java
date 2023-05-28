package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Collection;

@Entity(name = "courts")
public class Court extends AbstractEntity {

    @OneToMany(mappedBy = "court")
    private Collection<Booking> bookings;

    public Collection<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(final Collection<Booking> bookings) {
        this.bookings = bookings;
    }
}
