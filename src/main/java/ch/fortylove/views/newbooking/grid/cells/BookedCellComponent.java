package ch.fortylove.views.newbooking.grid.cells;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.entity.User;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import javax.annotation.Nonnull;
import java.util.List;

public class BookedCellComponent extends BookingCellComponent {

    public BookedCellComponent(@Nonnull final BookingDTO booking,
                               @Nonnull final ComponentEventListener<ClickEvent<HorizontalLayout>> clickListener) {
        super();

        constructUI(booking, clickListener);
    }

    private void constructUI(@Nonnull final BookingDTO booking,
                             @Nonnull final ComponentEventListener<ClickEvent<HorizontalLayout>> clickListener) {
        final AvatarGroup avatarGroup = new AvatarGroup();
        final List<User> users = booking.users();
        users.forEach(user -> avatarGroup.add(new AvatarGroup.AvatarGroupItem(user.getFullName())));

        addClickListener(clickListener);

        add(avatarGroup);
    }
}
