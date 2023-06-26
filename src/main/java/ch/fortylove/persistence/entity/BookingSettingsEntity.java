package ch.fortylove.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

@Entity(name = "bookingsettings")
public class BookingSettingsEntity extends AbstractEntity {

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "bookingsettings_id")
    private List<TimeSlotEntity> timeSlots;

    @Nonnull
    public List<TimeSlotEntity> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(@Nonnull final List<TimeSlotEntity> timeSlots) {
        this.timeSlots = timeSlots;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BookingSettingsEntity that = (BookingSettingsEntity) o;
        return Objects.equals(timeSlots, that.timeSlots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSlots);
    }
}
