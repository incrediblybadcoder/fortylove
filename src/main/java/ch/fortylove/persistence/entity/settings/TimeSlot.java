package ch.fortylove.persistence.entity.settings;

import ch.fortylove.persistence.entity.AbstractEntity;
import jakarta.persistence.Entity;

import javax.annotation.Nonnull;
import java.time.LocalTime;
import java.util.Objects;

@Entity(name = "timeslots")
public class TimeSlot extends AbstractEntity {

    private boolean bookable;
    private LocalTime time;

    public TimeSlot() {
        super();
    }

    public TimeSlot(final boolean bookable,
                    @Nonnull final LocalTime time) {
        this.bookable = bookable;
        this.time = time;
    }

    public boolean getBookable() {
        return bookable;
    }

    public void setBookable(final boolean bookable) {
        this.bookable = bookable;
    }

    @Nonnull
    public LocalTime getTime() {
        return time;
    }

    public void setTime(@Nonnull final LocalTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TimeSlot timeSlot = (TimeSlot) o;
        return bookable == timeSlot.bookable &&
                Objects.equals(time, timeSlot.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookable, time);
    }
}
