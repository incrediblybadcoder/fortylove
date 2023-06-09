package ch.fortylove.views.bookingoverview;

import ch.fortylove.persistence.entity.Booking;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BookingComponent extends HorizontalLayout {

    public BookingComponent(@Nonnull final Booking booking) {
        setSpacing(false);
        addClassName("booking");

        constructUI(booking);
    }

    private void constructUI(@Nonnull final Booking booking) {
        if (!booking.getUsers().isEmpty()) {
            final VerticalLayout playersContainer = new VerticalLayout();
            playersContainer.addClassName("players");
            playersContainer.setSpacing(false);
            playersContainer.setPadding(false);

            final List<com.vaadin.flow.component.Component> players = new ArrayList<>();
            booking.getUsers().forEach(player -> players.add(new Span(player.getFirstName())));

            playersContainer.add(players);
            add(playersContainer);
        }

        addClickListener(getBookingClickListener(booking));
    }

    private ComponentEventListener<ClickEvent<HorizontalLayout>> getBookingClickListener(@Nonnull final Booking booking) {
        return clickEvent -> {
            Notification.show(String.valueOf(booking.getTimeslot()));
//            dialog.open(booking);
        };
    }
}
