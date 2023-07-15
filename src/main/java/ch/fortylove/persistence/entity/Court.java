package ch.fortylove.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "courts")
@FilterDef(name = "bookingDateFilter", parameters = @ParamDef(name = "date", type = LocalDate.class), defaultCondition = "date = :date")
public class Court extends AbstractEntity {

    @NotNull
    @Column(name = "court_type")
    private CourtType courtType;

    @PositiveOrZero
    @Column(name = "number")
    private int number;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "court", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Filter(name = "bookingDateFilter")
    private List<Booking> bookings = new ArrayList<>();

    protected Court() {
        super();
    }

    public Court(@Nonnull final CourtType courtType,
                 final int number,
                 final String name) {
        super(UUID.randomUUID());
        this.courtType = courtType;
        this.number = number;
        this.name = name;
    }

    @Nonnull
    public CourtType getCourtType() {
        return courtType;
    }

    public void setCourtType(@Nonnull final CourtType courtType) {
        this.courtType = courtType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Nonnull
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(@Nonnull final List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(@Nonnull final Booking booking) {
        bookings.add(booking);
        booking.setCourt(this);
    }

    @Nonnull
    public String getIdentifier() {
        return "Platz " + getNumber() + " " + getName();
    }
}
