package ch.fortylove.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Objects;

@Entity(name = "bookingsettings")
public class BookingSettings extends AbstractEntity {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "bookingsettings_id")
    private List<Timeslot> timeslots;

    public BookingSettings() {
        super();
    }

    public BookingSettings(final long id,
                           final List<Timeslot> timeslots) {
        super(id, 0);
        this.timeslots = timeslots;
    }

    public List<Timeslot> getTimeSlots() {
        return timeslots;
    }

    public void setTimeSlots(final List<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BookingSettings that = (BookingSettings) o;
        return Objects.equals(timeslots, that.timeslots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeslots);
    }
}
