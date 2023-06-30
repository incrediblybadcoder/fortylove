package ch.fortylove.persistence.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class BookingDTO {

    private final long id;
    private final CourtDTO court;
    private final List<UserDTO> users;
    private final int timeSlotIndex;
    private final LocalDate date;

    public BookingDTO(final long id,
                      final CourtDTO court,
                      final List<UserDTO> users,
                      final int timeSlotIndex,
                      final LocalDate date) {
        this.id = id;
        this.court = court;
        this.users = users;
        this.timeSlotIndex = timeSlotIndex;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public CourtDTO getCourt() {
        return court;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public int getTimeSlotIndex() {
        return timeSlotIndex;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BookingDTO that = (BookingDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}