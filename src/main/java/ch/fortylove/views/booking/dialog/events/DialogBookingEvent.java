package ch.fortylove.views.booking.dialog.events;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.dto.TimeSlotDTO;
import ch.fortylove.views.booking.dialog.BookingDialog;
import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;

public class DialogBookingEvent extends ComponentEvent<BookingDialog> {

    @Nonnull private final CourtDTO courtDTO;
    @Nonnull private final TimeSlotDTO timeSlotDTO;
    @Nonnull private final BookingDTO booking;
    @Nonnull private final Type type;

    public enum Type {
        NEW,
        MODIFY,
        DELETE
    }

    protected DialogBookingEvent(@Nonnull final BookingDialog source,
                                 @Nonnull final CourtDTO courtDTO,
                                 @Nonnull final TimeSlotDTO timeSlotDTO,
                                 @Nonnull final BookingDTO booking,
                                 @Nonnull final Type type) {
        super(source, false);
        this.courtDTO = courtDTO;
        this.booking = booking;
        this.timeSlotDTO = timeSlotDTO;
        this.type = type;
    }

    public static DialogBookingEvent newBooking(@Nonnull final BookingDialog source,
                                                @Nonnull final CourtDTO courtDTO,
                                                @Nonnull final TimeSlotDTO timeSlotDTO,
                                                @Nonnull final BookingDTO booking) {
        return new DialogBookingEvent(source, courtDTO, timeSlotDTO, booking, Type.NEW);
    }

    public static DialogBookingEvent modifyBooking(@Nonnull final BookingDialog source,
                                                   @Nonnull final CourtDTO courtDTO,
                                                   @Nonnull final TimeSlotDTO timeSlotDTO,
                                                   @Nonnull final BookingDTO booking) {
        return new DialogBookingEvent(source, courtDTO, timeSlotDTO, booking, Type.MODIFY);
    }

    public static DialogBookingEvent deleteBooking(@Nonnull final BookingDialog source,
                                                   @Nonnull final CourtDTO courtDTO,
                                                   @Nonnull final TimeSlotDTO timeSlotDTO,
                                                   @Nonnull final BookingDTO booking) {
        return new DialogBookingEvent(source, courtDTO, timeSlotDTO, booking, Type.DELETE);
    }

    @Nonnull
    public CourtDTO getCourt() {
        return courtDTO;
    }

    @Nonnull
    public TimeSlotDTO getTimeSlot() {
        return timeSlotDTO;
    }

    @Nonnull
    public BookingDTO getBooking() {
        return booking;
    }

    @Nonnull
    public Type getType() {
        return type;
    }
}

