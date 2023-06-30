package ch.fortylove.views.booking.grid.cells;

import ch.fortylove.persistence.entity.Booking;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import javax.annotation.Nonnull;

public class BookedCellComponent extends BookingCellComponent {

    public BookedCellComponent(@Nonnull final Booking booking,
                               @Nonnull final ComponentEventListener<ClickEvent<HorizontalLayout>> clickListener) {
        super();

        constructUI(booking, clickListener);
    }

    private void constructUI(@Nonnull final Booking booking,
                             @Nonnull final ComponentEventListener<ClickEvent<HorizontalLayout>> clickListener) {
        final AvatarGroup avatarGroup = new AvatarGroup();
        avatarGroup.add(new AvatarGroup.AvatarGroupItem(booking.getOwner().getFullName()));
        booking.getOpponents().forEach(partner -> avatarGroup.add(new AvatarGroup.AvatarGroupItem(partner.getFullName())));

        addClickListener(clickListener);

        add(avatarGroup);
    }
}
