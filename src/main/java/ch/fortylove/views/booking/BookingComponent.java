package ch.fortylove.views.booking;

import ch.fortylove.views.booking.bookinggrid.BookingGridComponent;
import ch.fortylove.views.booking.dateselection.DateSelectionBrowseBackEvent;
import ch.fortylove.views.booking.dateselection.DateSelectionBrowseForwardEvent;
import ch.fortylove.views.booking.dateselection.DateSelectionComponent;
import ch.fortylove.views.booking.dateselection.DateSelectionPickerEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.LocalDate;

@Component
public class BookingComponent extends VerticalLayout {

    @Nonnull final private BookingGridComponent bookingGridComponent;
    private DateSelectionComponent dateSelectionComponent;

    @Nonnull private LocalDate date;

    @Autowired
    public BookingComponent(@Nonnull final BookingGridComponent bookingGridComponent) {
        this.bookingGridComponent = bookingGridComponent;
        date = LocalDate.now();

        addClassNames(
                LumoUtility.Background.ERROR
        );
        getStyle().set("overflow", "auto");

        constructUI();
//        setSpacing(false);
//        setPadding(false);
    }

    private void constructUI() {
        add(bookingGridComponent);
        add(createDateSelectionComponent());
    }

    @Nonnull
    private DateSelectionComponent createDateSelectionComponent() {
        dateSelectionComponent = new DateSelectionComponent(date);
        dateSelectionComponent.addBrowseBackButtonClickListener(this::dateBrowseBack);
        dateSelectionComponent.addBrowseForwardButtonClickListener(this::dateBrowseForward);
        dateSelectionComponent.addDatePickerValueChangeListener(this::selectDate);

        return dateSelectionComponent;
    }

    private void dateBrowseBack(@Nonnull final DateSelectionBrowseBackEvent event) {
        date = date.minusDays(1L);
        build();
    }

    private void dateBrowseForward(@Nonnull final DateSelectionBrowseForwardEvent event) {
        date = date.plusDays(1L);
        build();
    }

    private void selectDate(@Nonnull final DateSelectionPickerEvent dateSelectionPickerEvent) {
        date = dateSelectionPickerEvent.getLocalDate();
        build();
    }

    public void build() {
        bookingGridComponent.build(date);
        dateSelectionComponent.setDate(date);
    }
}
