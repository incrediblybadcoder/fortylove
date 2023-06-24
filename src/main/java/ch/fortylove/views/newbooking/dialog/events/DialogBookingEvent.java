package ch.fortylove.views.newbooking.dialog.events;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.entity.settings.TimeSlot;
import ch.fortylove.views.newbooking.dialog.BookingDialog;
import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;

public class DialogBookingEvent extends ComponentEvent<BookingDialog> {

    @Nonnull private final CourtDTO court;
    @Nonnull private final TimeSlot timeSlot;
    @Nonnull private final BookingDTO booking;
    @Nonnull private final Type type;

    public enum Type {
        NEW,
        MODIFY,
        DELETE
    }

    protected DialogBookingEvent(@Nonnull final BookingDialog source,
                                 @Nonnull final CourtDTO court,
                                 @Nonnull final TimeSlot timeSlot,
                                 @Nonnull final BookingDTO booking,
                                 @Nonnull final Type type) {
        super(source, false);
        this.court = court;
        this.booking = booking;
        this.timeSlot = timeSlot;
        this.type = type;
    }

    public static DialogBookingEvent newBooking(@Nonnull final BookingDialog source,
                                                @Nonnull final CourtDTO court,
                                                @Nonnull final TimeSlot timeSlot,
                                                @Nonnull final BookingDTO booking) {
        return new DialogBookingEvent(source, court, timeSlot, booking, Type.NEW);
    }

    public static DialogBookingEvent modifyBooking(@Nonnull final BookingDialog source,
                                                   @Nonnull final CourtDTO court,
                                                   @Nonnull final TimeSlot timeSlot,
                                                   @Nonnull final BookingDTO booking) {
        return new DialogBookingEvent(source, court, timeSlot, booking, Type.MODIFY);
    }

    public static DialogBookingEvent deleteBooking(@Nonnull final BookingDialog source,
                                                   @Nonnull final CourtDTO court,
                                                   @Nonnull final TimeSlot timeSlot,
                                                   @Nonnull final BookingDTO booking) {
        return new DialogBookingEvent(source, court, timeSlot, booking, Type.DELETE);
    }

    @Nonnull
    public CourtDTO getCourt() {
        return court;
    }

    @Nonnull
    public TimeSlot getTimeSlot() {
        return timeSlot;
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

