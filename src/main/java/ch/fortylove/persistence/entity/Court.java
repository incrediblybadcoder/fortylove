package ch.fortylove.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "court_type")
    private CourtType courtType;

    @Column(name = "has_ball_machine")
    private boolean hasBallMachine;

    @OneToMany(mappedBy = "court", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Filter(name = "bookingDateFilter")
    private List<Booking> bookings = new ArrayList<>();

    protected Court() {
        super();
    }

    public Court(@Nonnull final CourtType courtType,
                 final boolean hasBallMachine) {
        this.courtType = courtType;
        this.hasBallMachine = hasBallMachine;
    }

    @Nonnull
    public CourtType getCourtType() {
        return courtType;
    }

    public void setCourtType(@Nonnull final CourtType courtType) {
        this.courtType = courtType;
    }

    public boolean isHasBallMachine() {
        return hasBallMachine;
    }

    public void setHasBallMachine(final boolean hasBallMachine) {
        this.hasBallMachine = hasBallMachine;
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
        return getId().equals(court.getId()) &&
                hasBallMachine == court.hasBallMachine
                && courtType == court.courtType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), courtType, hasBallMachine);
    }

    public void addBooking(@Nonnull final Booking booking) {
        bookings.add(booking);
        booking.setCourt(this);
    }

    @Nonnull
    public String getIdentifier() {
        return "Platz " + getId();
    }
}
