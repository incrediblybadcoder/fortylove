package ch.fortylove.presentation.views.booking.bookingcomponent.grid.cells;

import ch.fortylove.persistence.entity.Booking;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.avatar.AvatarGroupVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class BookedCell extends BookableCell {

    private BookedCell(final boolean isActive,
                       final boolean isOwner,
                       @Nonnull final Booking booking,
                       @Nullable final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener) {
        super(isActive, clickListener);

        if (isActive && isOwner) {
            addClassName("booking-grid-bookable-cell-active-owner");
        }
        constructUI(booking);
    }

    @Nonnull
    public static BookedCell active(final boolean isOwner,
                                    @Nonnull final Booking booking,
                                    @Nonnull final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener) {
        return new BookedCell(true, isOwner, booking, clickListener);
    }

    @Nonnull
    public static BookedCell inActive(@Nonnull final Booking booking) {
        return new BookedCell(false, false, booking, null);
    }

    private void constructUI(@Nonnull final Booking booking) {
        final AvatarGroup avatarGroup = new AvatarGroup();
        avatarGroup.addThemeVariants(AvatarGroupVariant.LUMO_SMALL);
        avatarGroup.setMaxItemsVisible(2);
        avatarGroup.add(new AvatarGroup.AvatarGroupItem(booking.getOwner().getFullName()));
        booking.getOpponents().forEach(opponent -> avatarGroup.add(new AvatarGroup.AvatarGroupItem(opponent.getFullName())));

        add(avatarGroup);
    }
}
