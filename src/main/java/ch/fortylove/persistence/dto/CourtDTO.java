package ch.fortylove.persistence.dto;

import ch.fortylove.persistence.entity.Booking;

import javax.annotation.Nonnull;
import java.util.List;

public record CourtDTO(
        long id,
        @Nonnull List<Booking> bookings
) {
}
