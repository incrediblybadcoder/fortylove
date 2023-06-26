package ch.fortylove.persistence.dto;

import java.util.List;
import java.util.Objects;

public class BookingSettings {
    private final long id;
    private final List<TimeSlot> timeSlots;

    public BookingSettings(final long id,
                           final List<TimeSlot> timeSlots) {
        this.id = id;
        this.timeSlots = timeSlots;
    }

    public long getId() {
        return id;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BookingSettings that = (BookingSettings) o;
        return id == that.id &&
                Objects.equals(timeSlots, that.timeSlots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeSlots);
    }
}
