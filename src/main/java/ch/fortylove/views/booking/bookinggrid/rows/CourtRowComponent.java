package ch.fortylove.views.booking.bookinggrid.rows;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.settings.BookingSettings;
import ch.fortylove.persistence.entity.settings.TimeSlot;
import ch.fortylove.views.booking.bookinggrid.cells.BookedCellComponent;
import ch.fortylove.views.booking.bookinggrid.cells.BookingGridCellComponent;
import ch.fortylove.views.booking.bookinggrid.cells.CourtInfoCellComponent;
import ch.fortylove.views.booking.bookinggrid.cells.FreeCellComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourtRowComponent extends BookingGridRowComponent {

    public CourtRowComponent(@Nonnull final BookingSettings bookingSettings,
                             @Nonnull final Court court) {
        super();

        constructUI(bookingSettings, court);
    }

    private void constructUI(@Nonnull final BookingSettings bookingSettings,
                             @Nonnull final Court court) {
        setFirstCell(new CourtInfoCellComponent(court));

        final List<TimeSlot> timeSlots = bookingSettings.getTimeSlots();
        final List<Booking> bookings = court.getBookings();
        Collections.sort(bookings);

        final List<BookingGridCellComponent> allCells = getAllCells(bookings, timeSlots);
        final List<BookingGridCellComponent> visibleCells = allCells.stream().filter(BookingGridCellComponent::isVisible).toList();

        addCells(visibleCells);
    }

    @Nonnull
    private List<BookingGridCellComponent> getAllCells(@Nonnull final List<Booking> bookings,
                                                       @Nonnull final List<TimeSlot> timeSlots) {
        final List<BookingGridCellComponent> cells = new ArrayList<>();

        int counter = 0;
        for (final TimeSlot timeSlot : timeSlots) {
            final boolean isBookable = timeSlot.getBookable();

            if (bookings.size() > counter) {
                final Booking booking = bookings.get(counter);
                final int bookingHour = booking.getDateTime().getHour();

                if (bookingHour == timeSlot.getTime().getHour()) {
                    if (!isBookable) {
                        throw new IllegalStateException(String.format("Not bookable timeslot (%s) with booking (%s)", timeSlot, booking));
                    }
                    cells.add(new BookedCellComponent(booking, true));
                    counter++;
                } else {
                    cells.add(new FreeCellComponent(isBookable));
                }
            } else {
                cells.add(new FreeCellComponent(isBookable));
            }
        }
        return cells;
    }
}
