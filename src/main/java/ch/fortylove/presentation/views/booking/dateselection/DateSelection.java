package ch.fortylove.presentation.views.booking.dateselection;

import ch.fortylove.presentation.views.booking.dateselection.events.DateChangeEvent;
import ch.fortylove.util.DateTimeUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Locale;

@SpringComponent
@UIScope
public class DateSelection extends HorizontalLayout {

    private LocalDate date;
    private DatePicker datePicker;

    @Autowired
    public DateSelection(@Nonnull final DateTimeUtil dateTimeUtil) {
        date = dateTimeUtil.today();

        setWidthFull();
        addClassNames(LumoUtility.Padding.Top.LARGE, LumoUtility.Gap.MEDIUM);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        constructUI(date);
    }

    private void constructUI(@Nonnull final LocalDate date) {
        add(createBrowseBackButton(), createDatePicker(date), createBrowseForwardButton());
    }

    @Nonnull
    private DatePicker createDatePicker(final @Nonnull LocalDate date) {
        datePicker = new DatePicker(date);
        datePicker.setLocale(Locale.GERMAN);
        datePicker.addValueChangeListener(event -> {
            this.date = datePicker.getValue();
            fireEvent(new DateChangeEvent(this, date));
        });
        return datePicker;
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
