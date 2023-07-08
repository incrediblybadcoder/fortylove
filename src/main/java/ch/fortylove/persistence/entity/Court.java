package ch.fortylove.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "courts")
@FilterDef(name = "bookingDateFilter", parameters = @ParamDef(name = "date", type = LocalDate.class), defaultCondition = "date = :date")
public class Court extends AbstractEntity {

    @OneToMany(mappedBy = "court", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Filter(name = "bookingDateFilter")
    private List<Booking> bookings = new ArrayList<>();

    public Court() {
        super();
    }

    @Nonnull
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(@Nonnull final List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Court court = (Court) o;
        return Objects.equals(bookings, court.bookings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookings);
    }

    public void addBooking(@Nonnull final Booking booking) {
        bookings.add(booking);
        booking.setCourt(this);
    }
}
