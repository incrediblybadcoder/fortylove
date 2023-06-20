package ch.fortylove.views.bookingoverview;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.service.BookingService;
import ch.fortylove.persistence.service.UserService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingComponent extends HorizontalLayout {

    private final UserService userService;
    private final BookingService bookingService;

    Notification notification = new Notification(
            "Buchung wird gespeichert...", 3000);
    public BookingComponent(@Nonnull final Booking booking, UserService userService, final BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
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
            Optional<User> bookingPlayer = userService.findByEmail("jonas");
            bookingPlayer.ifPresent(user -> {
                final BookingDialog bookingDialog = new BookingDialog(booking, user , userService.findAll());
                bookingDialog.addSaveButtonClickListener(this::saveBooking);
                bookingDialog.open();
            });
        };
    }

    private void saveBooking(final BookingDialog.SaveEvent saveEvent) {
        System.out.println(saveEvent.getBooking());
        notification.open();
        bookingService.create(saveEvent.getBooking());
    }
}
