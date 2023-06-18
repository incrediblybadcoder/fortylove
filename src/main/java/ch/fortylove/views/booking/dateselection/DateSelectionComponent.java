package ch.fortylove.views.booking.dateselection;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.Locale;

public class DateSelectionComponent extends VerticalLayout {

    private DatePicker datePicker;

    public DateSelectionComponent(@Nonnull final LocalDate date) {
        super();
        addClassNames(
                LumoUtility.Background.BASE
        );

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
        datePicker.addValueChangeListener(event -> fireEvent(new DateSelectionPickerEvent(this, datePicker.getValue())));
    }

    @Nonnull
    private Button createBrowseBackButton() {
        return new Button(new Icon(VaadinIcon.ANGLE_LEFT), event -> fireEvent(new DateSelectionBrowseBackEvent(this)));
    }

    @Nonnull
    private Button createBrowseForwardButton() {
        return new Button(new Icon(VaadinIcon.ANGLE_RIGHT), event -> fireEvent(new DateSelectionBrowseForwardEvent(this)));
    }

    public void setDate(@Nonnull final LocalDate date) {
        datePicker.setValue(date);
    }

    @Nonnull
    public Registration addBrowseBackButtonClickListener(@Nonnull final ComponentEventListener<DateSelectionBrowseBackEvent> listener) {
        return addListener(DateSelectionBrowseBackEvent.class, listener);
    }

    @Nonnull
    public Registration addBrowseForwardButtonClickListener(@Nonnull final ComponentEventListener<DateSelectionBrowseForwardEvent> listener) {
        return addListener(DateSelectionBrowseForwardEvent.class, listener);
    }

    @Nonnull
    public Registration addDatePickerValueChangeListener(@Nonnull final ComponentEventListener<DateSelectionPickerEvent> listener) {
        return addListener(DateSelectionPickerEvent.class, listener);
    }
}
