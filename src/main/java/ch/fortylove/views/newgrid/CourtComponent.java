package ch.fortylove.views.newgrid;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.utility.CourtUtility;

import javax.annotation.Nonnull;
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

        final List<Booking> preparedBookings = CourtUtility.prepareBookings(bookingSettings.getNumberOfTimeslots(), court);
        preparedBookings.forEach(booking -> addCell(new BookingComponent(booking)));
    }
}
