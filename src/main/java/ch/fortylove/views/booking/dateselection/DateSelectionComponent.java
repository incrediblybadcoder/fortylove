package ch.fortylove.views.booking.dateselection;

import ch.fortylove.views.booking.dateselection.events.DateChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.Locale;

public class DateSelectionComponent extends VerticalLayout {

    private LocalDate date;
    private DatePicker datePicker;

    public DateSelectionComponent() {
        super();
        this.date = LocalDate.now();

        constructUI(date);
    }

    private void constructUI(@Nonnull final LocalDate date) {
        final HorizontalLayout horizontalLayout = new HorizontalLayout();

        final Button browseBackButton = createBrowseBackButton();
        final Button browseForwardButton = createBrowseForwardButton();
        createDatePicker(date);

        horizontalLayout.add(browseBackButton, datePicker, browseForwardButton);

        add(horizontalLayout);
    }

    private void createDatePicker(final @Nonnull LocalDate date) {
        datePicker = new DatePicker(date);
        datePicker.setLocale(Locale.GERMAN);
        datePicker.addValueChangeListener(event -> {
            this.date = datePicker.getValue();
            fireEvent(new DateChangeEvent(this, date));
        });
    }

    @Nonnull
    private Button createBrowseBackButton() {
        final ComponentEventListener<ClickEvent<Button>> clickListener = event -> {
            date = date.minusDays(1L);
            setDate(date);
            fireEvent(new DateChangeEvent(this, date));
        };
        return new Button(new Icon(VaadinIcon.ANGLE_LEFT), clickListener);
    }

    @Nonnull
    private Button createBrowseForwardButton() {
        final ComponentEventListener<ClickEvent<Button>> clickListener = event -> {
            date = date.plusDays(1L);
            setDate(date);
            fireEvent(new DateChangeEvent(this, date));
        };
        return new Button(new Icon(VaadinIcon.ANGLE_RIGHT), clickListener);
    }

    public void setDate(@Nonnull final LocalDate date) {
        datePicker.setValue(date);
    }

    public void addDateChangeListener(@Nonnull final ComponentEventListener<DateChangeEvent> listener) {
        addListener(DateChangeEvent.class, listener);
    }

    @Nonnull
    public LocalDate getDate() {
        return date;
    }
}
