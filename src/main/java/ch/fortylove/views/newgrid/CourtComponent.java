package ch.fortylove.views.newgrid;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.utility.CourtUtility;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.Nonnull;
import java.util.List;

public class CourtComponent extends HorizontalLayout {

    public CourtComponent(@Nonnull final Court court) {
        addClassNames(
                LumoUtility.Background.CONTRAST,
                LumoUtility.Padding.MEDIUM
        );
        setSpacing(false);

        constructUI(court);
    }

    private void constructUI(@Nonnull final Court court) {

        final List<Booking> preparedBookings = CourtUtility.prepareBookings(getAmountOfSlots(), court);
        preparedBookings.forEach(booking -> add(new BookingComponent(booking)));
    }

    private int getAmountOfSlots() {
        return 16;
    }
}
