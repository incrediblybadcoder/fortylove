package ch.fortylove.persistence.dto;

import java.util.List;
import java.util.Objects;

public class Court {

    private final long id;
    private final List<Booking> bookings;

    public Court(final long id, final List<Booking> bookings) {
        this.id = id;
        this.bookings = bookings;
    }

    public long getId() {
        return id;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Court court = (Court) o;
        return id == court.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
