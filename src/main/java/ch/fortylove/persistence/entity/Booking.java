package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;

@Entity(name = "bookings")
public class Booking extends AbstractEntity {

    @ManyToOne
    @JoinTable(name = "bookings_court", joinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "court_id", referencedColumnName = "id"))
    private Court court;

    private int timeslot;

//    private List<User> player;

    public Court getCourt() {
        return court;
    }

    public void setCourt(final Court court) {
        this.court = court;
    }

    public int getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(final int timeslot) {
        this.timeslot = timeslot;
    }

//    public List<User> getPlayer() {
//        return player;
//    }
//
//    public void setPlayer(final List<User> player) {
//        this.player = player;
//    }
}
