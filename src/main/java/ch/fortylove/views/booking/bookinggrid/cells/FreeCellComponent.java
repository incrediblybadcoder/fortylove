package ch.fortylove.views.booking.bookinggrid.cells;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.Nonnull;

public class FreeCellComponent extends BookingGridCellComponent {

    public FreeCellComponent(final boolean isVisible) {
        super(isVisible);

        addClassNames(LumoUtility.Border.RIGHT);

        constructUI();
    }

    private void constructUI() {
        addClickListener(getBookingClickListener());
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<HorizontalLayout>> getBookingClickListener() {
        return clickEvent -> Notification.show("free");
    }
}
