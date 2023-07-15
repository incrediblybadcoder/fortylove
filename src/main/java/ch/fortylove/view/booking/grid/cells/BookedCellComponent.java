package ch.fortylove.view.booking.grid.cells;

import ch.fortylove.persistence.entity.Booking;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.avatar.AvatarGroupVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.Nonnull;

public class BookedCellComponent extends BookingCellComponent {

    public BookedCellComponent(@Nonnull final Booking booking,
                               @Nonnull final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener) {
        super();

        constructUI(booking, clickListener);
    }

    private void constructUI(@Nonnull final Booking booking,
                             @Nonnull final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener) {
        final AvatarGroup avatarGroup = new AvatarGroup();
        avatarGroup.addThemeVariants(AvatarGroupVariant.LUMO_SMALL);
        avatarGroup.setMaxItemsVisible(2);
        avatarGroup.add(new AvatarGroup.AvatarGroupItem(booking.getOwner().getFullName()));
        booking.getOpponents().forEach(opponent -> avatarGroup.add(new AvatarGroup.AvatarGroupItem(opponent.getFullName())));

        addClickListener(clickListener);

        add(avatarGroup);
    }
}
