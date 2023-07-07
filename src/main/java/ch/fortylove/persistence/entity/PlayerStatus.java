package ch.fortylove.persistence.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity(name = "player_status")
public class PlayerStatus extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_status_id")
    private List<User> users;

    @Nonnull private int bookingsPerDay;
    @Nonnull private int bookableDaysInAdvance;


    public PlayerStatus(final long id,
                        final String name,
                        final List<User> users,
                        final int bookingsPerDay,
                        final int bookableDaysInAdvance) {
        super(id, 0);
        this.name = name;
        this.users = users;
        this.bookingsPerDay = bookingsPerDay;
        this.bookableDaysInAdvance = bookableDaysInAdvance;
    }

    public PlayerStatus() {

    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }

    public int getBookingsPerDay() {
        return bookingsPerDay;
    }

    public void setBookingsPerDay(final int bookingsPerDay) {
        this.bookingsPerDay = bookingsPerDay;
    }

    public int getBookableDaysInAdvance() {
        return bookableDaysInAdvance;
    }

    public void setBookableDaysInAdvance(final int bookableDaysInAdvance) {
        this.bookableDaysInAdvance = bookableDaysInAdvance;
    }
}
