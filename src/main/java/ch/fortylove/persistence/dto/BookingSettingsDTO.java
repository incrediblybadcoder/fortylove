package ch.fortylove.persistence.dto;

import java.util.List;
import java.util.Objects;

public class BookingSettingsDTO {
    private final long id;
    private final List<TimeSlotDTO> timeSlots;

    public BookingSettingsDTO(final long id,
                              final List<TimeSlotDTO> timeSlots) {
        this.id = id;
        this.timeSlots = timeSlots;
    }

    public long getId() {
        return id;
    }

    public List<TimeSlotDTO> getTimeSlots() {
        return timeSlots;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BookingSettingsDTO that = (BookingSettingsDTO) o;
        return id == that.id &&
                Objects.equals(timeSlots, that.timeSlots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeSlots);
    }
}
