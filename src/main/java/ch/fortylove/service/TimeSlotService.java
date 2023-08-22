package ch.fortylove.service;

import ch.fortylove.persistence.entity.Timeslot;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class TimeSlotService {

    public boolean isInPast(@Nonnull final LocalDateTime currentDateTime,
                            @Nonnull final LocalDate date,
                            @Nonnull final Timeslot timeslot) {
        final LocalDateTime bookingDateTime = LocalDateTime.of(date, timeslot.getStartTime());
        return bookingDateTime.isBefore(currentDateTime);
    }
}
