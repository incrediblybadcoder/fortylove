package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;

import javax.annotation.Nonnull;
import java.time.LocalTime;
import java.util.Objects;

@Entity(name = "timeslots")
public class TimeSlot extends AbstractEntity {

    public static final long MINUTES_PER_TIMESLOT = 60;
    public static final LocalTime BASE_TIME = LocalTime.of(0, 0);

    private boolean bookable;
    private int index;

    public TimeSlot() {
        super();
    }

    public TimeSlot(final long id,
                    final boolean bookable,
                    final int index) {
        super(id, 0);
        this.bookable = bookable;
        this.index = index;
    }

    public boolean getBookable() {
        return bookable;
    }

    public void setBookable(final boolean bookable) {
        this.bookable = bookable;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
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
        final TimeSlot timeSlot = (TimeSlot) o;
        return bookable == timeSlot.bookable && index == timeSlot.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookable, index);
    }
}
