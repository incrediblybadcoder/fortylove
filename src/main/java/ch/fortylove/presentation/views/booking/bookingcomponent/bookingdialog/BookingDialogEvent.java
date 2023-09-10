package ch.fortylove.presentation.views.booking.bookingcomponent.bookingdialog;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public class BookingDialogEvent extends ComponentEvent<BookingDialog> {

    @Nonnull private final Court court;
    @Nonnull private final Timeslot timeslot;
    @Nonnull private final Booking booking;
    @Nonnull private final Type type;

    public enum Type {
        NEW,
        MODIFY,
        DELETE
    }

    protected BookingDialogEvent(@Nonnull final BookingDialog source,
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

    @Nonnull
    public static BookingDialogEvent newBooking(@Nonnull final BookingDialog source,
                                                @Nonnull final Court court,
                                                @Nonnull final Timeslot timeslot,
                                                @Nonnull final Booking booking) {
        return new BookingDialogEvent(source, court, timeslot, booking, Type.NEW);
    }

    @Nonnull
    public static BookingDialogEvent modifyBooking(@Nonnull final BookingDialog source,
                                                   @Nonnull final Court court,
                                                   @Nonnull final Timeslot timeslot,
                                                   @Nonnull final Booking booking) {
        return new BookingDialogEvent(source, court, timeslot, booking, Type.MODIFY);
    }

    @Nonnull
    public static BookingDialogEvent deleteBooking(@Nonnull final BookingDialog source,
                                                   @Nonnull final Court court,
                                                   @Nonnull final Timeslot timeslot,
                                                   @Nonnull final Booking booking) {
        return new BookingDialogEvent(source, court, timeslot, booking, Type.DELETE);
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

