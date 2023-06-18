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

        final List<OverviewCellComponent> allCells = getAllCells(bookings, timeSlots);
        final List<OverviewCellComponent> visibleCells = allCells.stream().filter(OverviewCellComponent::isVisible).toList();

        addCells(visibleCells);
    }

    @Nonnull
    private List<OverviewCellComponent> getAllCells(@Nonnull final List<Booking> bookings,
                                                    @Nonnull final List<TimeSlot> timeSlots) {
        final List<OverviewCellComponent> cells = new ArrayList<>();

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
                    cells.add(new BookedBookingComponent(booking, true));
                    counter++;
                } else {
                    cells.add(new FreeBookingComponent(isBookable));
                }
            } else {
                cells.add(new FreeBookingComponent(isBookable));
            }
        }
        return cells;
    }
}
