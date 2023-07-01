package ch.fortylove.views.booking.events;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.views.booking.BookingComponent;
import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

public class BookingEvent extends ComponentEvent<BookingComponent> {

    @Nonnull private final Court court;
    @Nonnull private final LocalDate date;
    @Nonnull private final Timeslot timeslot;
    @Nullable private final Booking booking;
    @Nonnull private final Type type;

    public enum Type {
        FREE,
        EXISTING
    }

    protected BookingEvent(@Nonnull final BookingComponent source,
                           @Nonnull final Court court,
                           @Nonnull final LocalDate date,
                           @Nonnull final Timeslot timeslot,
                           @Nullable final Booking booking,
                           @Nonnull final Type type) {
        super(source, false);
        this.court = court;
        this.date = date;
        this.booking = booking;
        this.timeslot = timeslot;
        this.type = type;
    }

    public static BookingEvent freeBooking(@Nonnull final BookingComponent source,
                                           @Nonnull final Court court,
                                           @Nonnull final LocalDate date,
                                           @Nonnull final Timeslot timeslot) {
        return new BookingEvent(source, court, date, timeslot, null, Type.FREE);
    }

    public static BookingEvent existingBooking(@Nonnull final BookingComponent source,
                                               @Nonnull final Court court,
                                               @Nonnull final LocalDate date,
                                               @Nonnull final Timeslot timeslot,
                                               @Nonnull final Booking booking) {
        return new BookingEvent(source, court, date, timeslot, booking, Type.EXISTING);
    }

    @Nonnull
    public Court getCourt() {
        return court;
    }

    @Nonnull
    public Timeslot getTimeSlot() {
        return timeslot;
    }

    @Nullable
    public Optional<Booking> getBooking() {
        return Optional.ofNullable(booking);
    }

    @Nonnull
    public Type getType() {
        return type;
    }

    @Nonnull
    public LocalDate getDate() {
        return date;
    }
}

