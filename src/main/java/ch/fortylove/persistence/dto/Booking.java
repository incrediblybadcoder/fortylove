package ch.fortylove.persistence.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Booking {

    private final long id;
    private final Court court;
    private final List<User> users;
    private final int timeSlotIndex;
    private final LocalDate date;

    public Booking(final long id,
                   final Court court,
                   final List<User> users,
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

    public Court getCourt() {
        return court;
    }

    public List<User> getUsers() {
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
        final Booking that = (Booking) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}