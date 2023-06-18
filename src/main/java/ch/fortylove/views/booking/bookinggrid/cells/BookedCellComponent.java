package ch.fortylove.views.booking.bookinggrid.cells;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.User;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.Nonnull;
import java.util.List;

public class BookedCellComponent extends BookingGridCellComponent {

    public BookedCellComponent(@Nonnull final Booking booking,
                               final boolean isVisible) {
        super(isVisible);

        addClassNames(
                LumoUtility.Background.PRIMARY
        );
        setAlignItems(Alignment.CENTER);

        constructUI(booking);
    }

    private void constructUI(@Nonnull final Booking booking) {
        final AvatarGroup avatarGroup = new AvatarGroup();

        final List<User> users = booking.getUsers();
        for (int i = 0; i < users.size(); i++) {
            final AvatarGroup.AvatarGroupItem avatarGroupItem = new AvatarGroup.AvatarGroupItem(users.get(i).getFullName());
            avatarGroupItem.setColorIndex(i);
            avatarGroup.add(avatarGroupItem);
        }

        add(avatarGroup);

        addClickListener(getBookingClickListener(booking));
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<HorizontalLayout>> getBookingClickListener(@Nonnull final Booking booking) {
        return clickEvent -> Notification.show(String.valueOf(booking.getDateTime()));
    }
}
