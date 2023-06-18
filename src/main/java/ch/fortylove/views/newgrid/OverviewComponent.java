package ch.fortylove.views.newgrid;

import ch.fortylove.views.newgrid.dateselection.DateSelectionBrowseBackEvent;
import ch.fortylove.views.newgrid.dateselection.DateSelectionBrowseForwardEvent;
import ch.fortylove.views.newgrid.dateselection.DateSelectionComponent;
import ch.fortylove.views.newgrid.dateselection.DateSelectionPickerEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.LocalDate;

@Component
public class OverviewComponent extends VerticalLayout {

    @Nonnull final private OverviewGridComponent overviewGridComponent;
    private DateSelectionComponent dateSelectionComponent;

    @Nonnull private LocalDate date;

    @Autowired
    public OverviewComponent(@Nonnull final OverviewGridComponent overviewGridComponent) {
        this.overviewGridComponent = overviewGridComponent;
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
        add(overviewGridComponent);
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
        overviewGridComponent.build(date);
        dateSelectionComponent.setDate(date);
    }
}
