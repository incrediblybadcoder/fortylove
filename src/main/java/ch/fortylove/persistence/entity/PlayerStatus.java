package ch.fortylove.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "player_status")
public class PlayerStatus extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "bookings_per_day")
    private int bookingsPerDay;

    @Column(name = "bookable_days_in_advance")
    private int bookableDaysInAdvance;

    @OneToMany(mappedBy = "playerStatus", fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    public PlayerStatus() {
        super();
    }

    public PlayerStatus(@Nonnull final String name,
                        final int bookingsPerDay,
                        final int bookableDaysInAdvance) {
        super();
        this.name = name;
        this.bookingsPerDay = bookingsPerDay;
        this.bookableDaysInAdvance = bookableDaysInAdvance;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull final String name) {
        this.name = name;
    }

    @Nonnull
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(@Nonnull final List<User> users) {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PlayerStatus that = (PlayerStatus) o;
        return bookingsPerDay == that.bookingsPerDay &&
                bookableDaysInAdvance == that.bookableDaysInAdvance &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, bookingsPerDay, bookableDaysInAdvance);
    }
}
