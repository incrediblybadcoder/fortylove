package ch.fortylove.views.newgrid.rows;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.settings.BookingSettings;
import ch.fortylove.persistence.entity.settings.TimeSlot;
import ch.fortylove.views.newgrid.cells.BookedBookingComponent;
import ch.fortylove.views.newgrid.cells.CourtInfoComponent;
import ch.fortylove.views.newgrid.cells.FreeBookingComponent;
import ch.fortylove.views.newgrid.cells.OverviewCellComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourtComponent extends OverviewRowComponent {

    public CourtComponent(@Nonnull final BookingSettings bookingSettings,
                          @Nonnull final Court court) {
        super();

        constructUI(bookingSettings, court);
    }

    private void constructUI(@Nonnull final BookingSettings bookingSettings,
                             @Nonnull final Court court) {
        setFirstCell(new CourtInfoComponent(court));

        final List<TimeSlot> timeSlots = bookingSettings.getTimeSlots();
        final List<Booking> bookings = court.getBookings();
        Collections.sort(bookings);

        final List<OverviewCellComponent> cells = getCells(bookings, timeSlots);

        addCells(cells);
    }

    @Nonnull
    private static List<OverviewCellComponent> getCells(@Nonnull final List<Booking> bookings,
                                                        @Nonnull final List<TimeSlot> timeSlots) {
        final List<OverviewCellComponent> cells = new ArrayList<>();

        int counter = 0;
        for (final TimeSlot timeSlot : timeSlots) {
            if (bookings.size() > counter) {

                final Booking booking = bookings.get(counter);
                final int bookingHour = booking.getDateTime().getHour();

                if (bookingHour == timeSlot.getTime().getHour()) {
                    if (!timeSlot.getBookable()) {
                        throw new IllegalStateException(String.format("Not bookable timeslot (%s) with booking (%s)", timeSlot, booking));
                    }
                    cells.add(new BookedBookingComponent(booking));
                    counter++;
                } else {
                    cells.add(new FreeBookingComponent());
                }
            } else {
                cells.add(new FreeBookingComponent());
            }
        }
        return cells;
    }
}
