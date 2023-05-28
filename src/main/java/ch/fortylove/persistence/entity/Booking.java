package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import javax.annotation.Nonnull;
import java.util.Collection;

@Entity(name = "bookings")
public class Booking extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "court_id")
    private Court court;

    @ManyToMany(mappedBy = "bookings")
    private Collection<User> users;

    private int timeslot;

    @Nonnull
    public Court getCourt() {
        return court;
    }

    public void setCourt(@Nonnull final Court court) {
        this.court = court;
    }

    public int getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(final int timeslot) {
        this.timeslot = timeslot;
    }

    @Nonnull
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(@Nonnull final Collection<User> users) {
        this.users = users;
    }
}
