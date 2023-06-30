package ch.fortylove.views.booking.events;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.dto.TimeSlotDTO;
import ch.fortylove.views.booking.BookingComponent;
import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

public class BookingEvent extends ComponentEvent<BookingComponent> {

    @Nonnull private final CourtDTO courtDTO;
    @Nonnull private final LocalDate date;
    @Nonnull private final TimeSlotDTO timeSlotDTO;
    @Nullable private final BookingDTO booking;
    @Nonnull private final Type type;

    public enum Type {
        FREE,
        EXISTING
    }

    protected BookingEvent(@Nonnull final BookingComponent source,
                           @Nonnull final CourtDTO courtDTO,
                           @Nonnull final LocalDate date,
                           @Nonnull final TimeSlotDTO timeSlotDTO,
                           @Nullable final BookingDTO booking,
                           @Nonnull final Type type) {
        super(source, false);
        this.courtDTO = courtDTO;
        this.date = date;
        this.booking = booking;
        this.timeSlotDTO = timeSlotDTO;
        this.type = type;
    }

    public static BookingEvent freeBooking(@Nonnull final BookingComponent source,
                                           @Nonnull final CourtDTO courtDTO,
                                           @Nonnull final LocalDate date,
                                           @Nonnull final TimeSlotDTO timeSlotDTO) {
        return new BookingEvent(source, courtDTO, date, timeSlotDTO, null, Type.FREE);
    }

    public static BookingEvent existingBooking(@Nonnull final BookingComponent source,
                                               @Nonnull final CourtDTO courtDTO,
                                               @Nonnull final LocalDate date,
                                               @Nonnull final TimeSlotDTO timeSlotDTO,
                                               @Nonnull final BookingDTO booking) {
        return new BookingEvent(source, courtDTO, date, timeSlotDTO, booking, Type.EXISTING);
    }

    @Nonnull
    public CourtDTO getCourt() {
        return courtDTO;
    }

    @Nonnull
    public TimeSlotDTO getTimeSlot() {
        return timeSlotDTO;
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

