package ch.fortylove.view.booking.dialog.events;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.view.booking.dialog.BookingDialog;
import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;

public class DialogBookingEvent extends ComponentEvent<BookingDialog> {

    @Nonnull private final Court court;
    @Nonnull private final Timeslot timeslot;
    @Nonnull private final Booking booking;
    @Nonnull private final Type type;

    public enum Type {
        NEW,
        MODIFY,
        DELETE
    }

    protected DialogBookingEvent(@Nonnull final BookingDialog source,
                                 @Nonnull final Court court,
                                 @Nonnull final Timeslot timeslot,
                                 @Nonnull final Booking booking,
                                 @Nonnull final Type type) {
        super(source, false);
        this.court = court;
        this.booking = booking;
        this.timeslot = timeslot;
        this.type = type;
    }

    public static DialogBookingEvent newBooking(@Nonnull final BookingDialog source,
                                                @Nonnull final Court court,
                                                @Nonnull final Timeslot timeslot,
                                                @Nonnull final Booking booking) {
        return new DialogBookingEvent(source, court, timeslot, booking, Type.NEW);
    }

    public static DialogBookingEvent modifyBooking(@Nonnull final BookingDialog source,
                                                   @Nonnull final Court court,
                                                   @Nonnull final Timeslot timeslot,
                                                   @Nonnull final Booking booking) {
        return new DialogBookingEvent(source, court, timeslot, booking, Type.MODIFY);
    }

    public static DialogBookingEvent deleteBooking(@Nonnull final BookingDialog source,
                                                   @Nonnull final Court court,
                                                   @Nonnull final Timeslot timeslot,
                                                   @Nonnull final Booking booking) {
        return new DialogBookingEvent(source, court, timeslot, booking, Type.DELETE);
    }

    @Nonnull
    public Court getCourt() {
        return court;
    }

    @Nonnull
    public Timeslot getTimeSlot() {
        return timeslot;
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

