package ch.fortylove.views.newbooking.events;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.entity.settings.TimeSlot;
import ch.fortylove.views.newbooking.BookingComponent;
import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

public class BookingEvent extends ComponentEvent<BookingComponent> {

    @Nonnull private final CourtDTO court;
    @Nonnull private final LocalDate date;
    @Nonnull private final TimeSlot timeSlot;
    @Nullable private final BookingDTO booking;
    @Nonnull private final Type type;

    public enum Type {
        FREE,
        EXISTING
    }

    protected BookingEvent(@Nonnull final BookingComponent source,
                           @Nonnull final CourtDTO court,
                           @Nonnull final LocalDate date,
                           @Nonnull final TimeSlot timeSlot,
                           @Nullable final BookingDTO booking,
                           @Nonnull final Type type) {
        super(source, false);
        this.court = court;
        this.date = date;
        this.booking = booking;
        this.timeSlot = timeSlot;
        this.type = type;
    }

    public static BookingEvent freeBooking(@Nonnull final BookingComponent source,
                                           @Nonnull final CourtDTO court,
                                           @Nonnull final LocalDate date,
                                           @Nonnull final TimeSlot timeSlot) {
        return new BookingEvent(source, court, date, timeSlot, null, Type.FREE);
    }

    public static BookingEvent existingBooking(@Nonnull final BookingComponent source,
                                               @Nonnull final CourtDTO court,
                                               @Nonnull final LocalDate date,
                                               @Nonnull final TimeSlot timeSlot,
                                               @Nonnull final BookingDTO booking) {
        return new BookingEvent(source, court, date, timeSlot, booking, Type.EXISTING);
    }

    @Nonnull
    public CourtDTO getCourt() {
        return court;
    }

    @Nonnull
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    @Nullable
    public Optional<BookingDTO> getBooking() {
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

