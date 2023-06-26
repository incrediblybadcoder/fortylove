package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;

import java.util.Objects;

@Entity(name = "timeslots")
public class TimeSlot extends AbstractEntity {

    private boolean bookable;
    private int index;

    public TimeSlot() {
        super();
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
