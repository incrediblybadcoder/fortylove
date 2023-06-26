package ch.fortylove.views.booking.events;

import ch.fortylove.persistence.dto.Booking;
import ch.fortylove.persistence.dto.Court;
import ch.fortylove.persistence.dto.TimeSlot;
import ch.fortylove.views.booking.BookingComponent;
import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

public class BookingEvent extends ComponentEvent<BookingComponent> {

    @Nonnull private final Court court;
    @Nonnull private final LocalDate date;
    @Nonnull private final TimeSlot timeSlot;
    @Nullable private final Booking booking;
    @Nonnull private final Type type;

    public enum Type {
        FREE,
        EXISTING
    }

    protected BookingEvent(@Nonnull final BookingComponent source,
                           @Nonnull final Court court,
                           @Nonnull final LocalDate date,
                           @Nonnull final TimeSlot timeSlot,
                           @Nullable final Booking booking,
                           @Nonnull final Type type) {
        super(source, false);
        this.court = court;
        this.date = date;
        this.booking = booking;
        this.timeSlot = timeSlot;
        this.type = type;
    }

    public static BookingEvent freeBooking(@Nonnull final BookingComponent source,
                                           @Nonnull final Court court,
                                           @Nonnull final LocalDate date,
                                           @Nonnull final TimeSlot timeSlot) {
        return new BookingEvent(source, court, date, timeSlot, null, Type.FREE);
    }

    public static BookingEvent existingBooking(@Nonnull final BookingComponent source,
                                               @Nonnull final Court court,
                                               @Nonnull final LocalDate date,
                                               @Nonnull final TimeSlot timeSlot,
                                               @Nonnull final Booking booking) {
        return new BookingEvent(source, court, date, timeSlot, booking, Type.EXISTING);
    }

    @Nonnull
    public Court getCourt() {
        return court;
    }

    @Nonnull
    public TimeSlot getTimeSlot() {
        return timeSlot;
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

