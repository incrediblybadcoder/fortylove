package ch.fortylove.persistence.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

@Entity(name = "bookingsettings")
public class BookingSettings extends AbstractEntity {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "bookingsettings_id", nullable=false)
    private SortedSet<Timeslot> timeslots = new TreeSet<>();

    protected BookingSettings() {
    }

    public BookingSettings(@Nonnull final SortedSet<Timeslot> timeslots) {
        super(UUID.randomUUID());
        this.timeslots = timeslots;
    }

    @Nonnull
    public SortedSet<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(@Nonnull final SortedSet<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }
}
