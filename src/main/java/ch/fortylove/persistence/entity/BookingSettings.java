package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;

import javax.annotation.Nonnull;
import java.time.LocalTime;
import java.util.Objects;

@Entity(name = "bookingsettings")
public class BookingSettings extends AbstractEntity {

    private int numberOfTimeslots;
    private LocalTime startHour;

    public BookingSettings() {
        super();
    }

    public BookingSettings(final int numberOfTimeslots,
                           @Nonnull final LocalTime startHour) {
        this.numberOfTimeslots = numberOfTimeslots;
        this.startHour = startHour;
    }

    public int getNumberOfTimeslots() {
        return numberOfTimeslots;
    }

    public void setNumberOfTimeslots(final int numberOfTimeslots) {
        this.numberOfTimeslots = numberOfTimeslots;
    }

    @Nonnull
    public LocalTime getStartHour() {
        return startHour;
    }

    public void setStartHour(@Nonnull final LocalTime startHour) {
        this.startHour = startHour;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BookingSettings that = (BookingSettings) o;
        return numberOfTimeslots == that.numberOfTimeslots;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfTimeslots);
    }
}
