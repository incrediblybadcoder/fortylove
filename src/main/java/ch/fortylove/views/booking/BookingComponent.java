package ch.fortylove.views.booking;

import ch.fortylove.persistence.service.CourtService;
import ch.fortylove.persistence.service.settings.BookingSettingsService;
import ch.fortylove.views.booking.bookinggrid.BookingGridComponent;
import ch.fortylove.views.booking.dateselection.DateSelectionComponent;
import ch.fortylove.views.booking.dateselection.events.DateSelectionBrowseBackEvent;
import ch.fortylove.views.booking.dateselection.events.DateSelectionBrowseForwardEvent;
import ch.fortylove.views.booking.dateselection.events.DateSelectionPickerEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.Nonnull;
import java.time.LocalDate;

public class BookingComponent extends VerticalLayout {

    @Nonnull private final CourtService courtService;
    @Nonnull private final BookingSettingsService bookingSettingsService;

    private BookingGridComponent bookingGridComponent;
    private DateSelectionComponent dateSelectionComponent;

    @Nonnull private LocalDate date;

    public BookingComponent(@Nonnull final CourtService courtService,
                            @Nonnull final BookingSettingsService bookingSettingsService) {
        this.courtService = courtService;
        this.bookingSettingsService = bookingSettingsService;
        date = LocalDate.now();

        constructUI();
        setSpacing(false);
        setPadding(false);
    }

    private void constructUI() {
        add(createBookingGridComponent());
        add(createDateSelectionComponent());
    }

    @Nonnull
    private BookingGridComponent createBookingGridComponent() {
        bookingGridComponent = new BookingGridComponent(courtService, bookingSettingsService);

        return bookingGridComponent;
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
