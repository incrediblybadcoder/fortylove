package ch.fortylove.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.PositiveOrZero;

import javax.annotation.Nonnull;
import java.time.LocalTime;
import java.util.UUID;

@Entity(name = "timeslots")
public class Timeslot extends AbstractEntity implements Comparable<Timeslot> {

    @Nonnull public static final LocalTime BASE_TIME = LocalTime.of(0, 0);
    public static final long MINUTES_PER_TIMESLOT = 60;

    @Column(name = "bookable")
    private boolean bookable;

    @PositiveOrZero
    @Column(name = "index")
    private int index;

    protected Timeslot() {
    }

    public Timeslot(final boolean bookable,
                    final int index) {
        super(UUID.randomUUID());
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
    public int compareTo(@Nonnull final Timeslot timeslot) {
        return Integer.compare(index, timeslot.getIndex());
    }

    @Override
    public String toString() {
        return "Timeslot{" +
                "bookable=" + bookable +
                ", index=" + index +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                '}';
    }
}
