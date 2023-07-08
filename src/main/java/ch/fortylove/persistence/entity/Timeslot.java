package ch.fortylove.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import javax.annotation.Nonnull;
import java.time.LocalTime;
import java.util.Objects;

@Entity(name = "timeslots")
public class Timeslot extends AbstractEntity {

    @Nonnull public static final LocalTime BASE_TIME = LocalTime.of(0, 0);
    public static final long MINUTES_PER_TIMESLOT = 60;

    @Column(name = "bookable")
    private boolean bookable;

    @Column(name = "index")
    private int index;

    public Timeslot() {
        super();
    }

    public Timeslot(final boolean bookable,
                    final int index) {
        super();
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

    public String getTimeIntervalText() {
        return getStartTime() + " - " + getEndTime();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Timeslot timeslot = (Timeslot) o;
        return bookable == timeslot.bookable &&
                index == timeslot.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookable, index);
    }
}
