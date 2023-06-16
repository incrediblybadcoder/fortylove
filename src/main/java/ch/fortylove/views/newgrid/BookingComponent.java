package ch.fortylove.views.newgrid;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.views.components.ShortenedLabel;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.Nonnull;
import java.util.List;

public class BookingComponent extends HorizontalLayout {

    public BookingComponent(@Nonnull final Booking booking) {
        addClassNames(
                LumoUtility.Background.PRIMARY
        );
        setSpacing(false);

        constructUI(booking);
    }

    private void constructUI(@Nonnull final Booking booking) {
        final VerticalLayout playerInfoContainer = getPlayerInfoContainer(booking.getUsers());
        final VerticalLayout bookingInfoContainer = getAdditionalInfoContainer();

        add(playerInfoContainer, bookingInfoContainer);

        addClickListener(getBookingClickListener(booking));
    }

    @Nonnull
    private VerticalLayout getPlayerInfoContainer(@Nonnull final List<User> users) {
        final VerticalLayout playerInfoContainer = new VerticalLayout();
        playerInfoContainer.setSpacing(false);
        playerInfoContainer.setPadding(false);
        playerInfoContainer.addClassNames(
                LumoUtility.Background.BASE
        );

        users.forEach(user -> {
            final ShortenedLabel playerLabel = new ShortenedLabel(user.getFirstName());
            playerInfoContainer.add(playerLabel);
        });

        return playerInfoContainer;
    }

    private VerticalLayout getAdditionalInfoContainer() {
        final VerticalLayout additionalInfoContainer = new VerticalLayout();
        additionalInfoContainer.setSpacing(false);
        additionalInfoContainer.setPadding(false);
        additionalInfoContainer.addClassNames(
                LumoUtility.Background.CONTRAST_50
        );

        final Span statusBadge = new Span("Einzel");
        statusBadge.getElement().getThemeList().add("badge small success primary");

        additionalInfoContainer.add(statusBadge);
        return additionalInfoContainer;
    }

    private ComponentEventListener<ClickEvent<HorizontalLayout>> getBookingClickListener(@Nonnull final Booking booking) {
        return clickEvent -> {
            Notification.show(String.valueOf(booking.getTimeslot()));
        };
    }
}
