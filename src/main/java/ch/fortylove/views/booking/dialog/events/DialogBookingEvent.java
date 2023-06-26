package ch.fortylove.views.booking.dialog.events;

import ch.fortylove.persistence.dto.Booking;
import ch.fortylove.persistence.dto.Court;
import ch.fortylove.persistence.dto.TimeSlot;
import ch.fortylove.views.booking.dialog.BookingDialog;
import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;

public class DialogBookingEvent extends ComponentEvent<BookingDialog> {

    @Nonnull private final Court court;
    @Nonnull private final TimeSlot timeSlot;
    @Nonnull private final Booking booking;
    @Nonnull private final Type type;

    public enum Type {
        NEW,
        MODIFY,
        DELETE
    }

    protected DialogBookingEvent(@Nonnull final BookingDialog source,
                                 @Nonnull final Court court,
                                 @Nonnull final TimeSlot timeSlot,
                                 @Nonnull final Booking booking,
                                 @Nonnull final Type type) {
        super(source, false);
        this.court = court;
        this.booking = booking;
        this.timeSlot = timeSlot;
        this.type = type;
    }

    public static DialogBookingEvent newBooking(@Nonnull final BookingDialog source,
                                                @Nonnull final Court court,
                                                @Nonnull final TimeSlot timeSlot,
                                                @Nonnull final Booking booking) {
        return new DialogBookingEvent(source, court, timeSlot, booking, Type.NEW);
    }

    public static DialogBookingEvent modifyBooking(@Nonnull final BookingDialog source,
                                                   @Nonnull final Court court,
                                                   @Nonnull final TimeSlot timeSlot,
                                                   @Nonnull final Booking booking) {
        return new DialogBookingEvent(source, court, timeSlot, booking, Type.MODIFY);
    }

    public static DialogBookingEvent deleteBooking(@Nonnull final BookingDialog source,
                                                   @Nonnull final Court court,
                                                   @Nonnull final TimeSlot timeSlot,
                                                   @Nonnull final Booking booking) {
        return new DialogBookingEvent(source, court, timeSlot, booking, Type.DELETE);
    }

    @Nonnull
    public Court getCourt() {
        return court;
    }

    @Nonnull
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    @Nonnull
    public Booking getBooking() {
        return booking;
    }

    @Nonnull
    public Type getType() {
        return type;
    }
}

