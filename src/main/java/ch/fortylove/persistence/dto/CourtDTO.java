package ch.fortylove.persistence.dto;

import java.util.List;
import java.util.Objects;

public class CourtDTO {

    private final long id;
    private final List<BookingDTO> bookings;

    public CourtDTO(final long id, final List<BookingDTO> bookings) {
        this.id = id;
        this.bookings = bookings;
    }

    public long getId() {
        return id;
    }

    public List<BookingDTO> getBookings() {
        return bookings;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CourtDTO courtDTO = (CourtDTO) o;
        return id == courtDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
