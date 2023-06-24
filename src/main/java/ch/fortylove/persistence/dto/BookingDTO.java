package ch.fortylove.persistence.dto;

import ch.fortylove.persistence.entity.User;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;

public record BookingDTO(
        @Nonnull CourtDTO court,
        @Nonnull List<User> users,
        int timeSlotIndex,
        @Nonnull LocalDate date
) {
}