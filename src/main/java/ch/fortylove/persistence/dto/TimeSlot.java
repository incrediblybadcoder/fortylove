package ch.fortylove.persistence.dto;

import javax.annotation.Nonnull;
import java.time.LocalTime;
import java.util.Objects;

public class TimeSlot {

    public static final long MINUTES_PER_TIMESLOT = 60;
    public static final LocalTime BASE_TIME = LocalTime.of(0, 0);

    private final long id;
    private final int index;
    private final boolean bookable;

    public TimeSlot(final long id,
                    final boolean bookable,
                    final int index) {
        this.id = id;
        this.bookable = bookable;
        this.index = index;
    }

    public long getId() {
        return id;
    }

    public boolean getBookable() {
        return bookable;
    }

    public int getIndex() {
        return index;
    }

    public static int getTotalNumberOfTimeSlots() {
        final int totalNumbersOfMinutes= 24 * 60;
        return (int) (totalNumbersOfMinutes / MINUTES_PER_TIMESLOT);
    }

    @Nonnull
    public LocalTime getStartTime() {
        return BASE_TIME.plusMinutes(index * MINUTES_PER_TIMESLOT);
    }

    public LocalTime getEndTime() {
        return BASE_TIME.plusMinutes((index + 1) * MINUTES_PER_TIMESLOT);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TimeSlot that = (TimeSlot) o;
        return id == that.id &&
                index == that.index &&
                bookable == that.bookable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, index, bookable);
    }
}
