package ch.fortylove.presentation.views.management.courtmanagement;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtIcon;
import ch.fortylove.presentation.components.managementform.events.ManagementFormDeleteEvent;
import ch.fortylove.presentation.components.managementform.events.ManagementFormModifyEvent;
import ch.fortylove.presentation.components.managementform.events.ManagementFormSaveEvent;
import ch.fortylove.service.CourtService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CourtManagementView extends VerticalLayout {

    @Nonnull private final CourtService courtService;
    @Nonnull private final CourtForm courtForm;

    private Grid<Court> grid;

    public CourtManagementView(@Nonnull final CourtService courtService,
                               @Nonnull final CourtForm courtForm) {
        this.courtService = courtService;
        this.courtForm = courtForm;

        setSizeFull();
        setPadding(false);
        addClassName(LumoUtility.Padding.Top.MEDIUM);

        constructUI();
    }

    private void constructUI() {
        configureGrid();
        configureForm();

        final HorizontalLayout content = new HorizontalLayout(grid, courtForm);
        content.addClassName("content");
        content.setSizeFull();

        add(content);
        updateCourtList();
    }

    private void configureForm() {
        courtForm.addSaveEventListener(this::saveEvent);
        courtForm.addModifyEventListener(this::updateEvent);
        courtForm.addDeleteEventListener(this::deleteEvent);
    }

    private void updateCourtList() {
        grid.setItems(courtService.findAll());
    }

    private void configureGrid() {
        grid = new Grid<>(Court.class, false);
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        final Grid.Column<Court> numberColumn = grid.addColumn(Court::getNumber)
                .setHeader("Nummer")
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setSortable(true);

        final Grid.Column<Court> iconColumn = grid.addComponentColumn(court -> getIconComponent(court.getCourtIcon()))
                .setHeader("Icon")
                .setSortable(true)
                .setAutoWidth(true)
                .setFlexGrow(0);

        final Grid.Column<Court> typeColumn = grid.addColumn(court -> court.getCourtType().getMaterial())
                .setHeader("Material")
                .setSortable(true);

        final Grid.Column<Court> nameColumn = grid.addColumn(Court::getName)
                .setHeader("Name")
                .setSortable(true);

        createGridFooter(numberColumn, iconColumn, typeColumn, nameColumn);

        grid.asSingleSelect().addValueChangeListener(event -> editCourt(event.getValue()));
    }

    @Nonnull
    private Image getIconComponent(@Nonnull final CourtIcon courtIcon) {
        final Image icon = new Image(courtIcon.getResource(), courtIcon.getCode());
        icon.setHeight("1em");
        return icon;
    }

    private void createGridFooter(@Nonnull final Grid.Column<Court> numberColumn,
                                  @Nonnull final Grid.Column<Court> iconColumn,
                                  @Nonnull final Grid.Column<Court> typeColumn,
                                  @Nonnull final Grid.Column<Court> nameColumn) {
        grid.appendFooterRow();
        final FooterRow footerRow = grid.appendFooterRow();
        final FooterRow.FooterCell footerCell = footerRow.join(numberColumn, iconColumn, typeColumn, nameColumn);

        final Button addButton = new Button("Erstellen", new Icon(VaadinIcon.PLUS_CIRCLE), click -> addCourt());
        addButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        final HorizontalLayout horizontalLayout = new HorizontalLayout(addButton);
        horizontalLayout.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_20, LumoUtility.Padding.Left.SMALL, LumoUtility.Padding.Right.SMALL);
        footerCell.setComponent(horizontalLayout);
    }

    private void addCourt() {
        grid.asSingleSelect().clear();
        courtForm.openCreate();
    }

    private void editCourt(@Nullable final Court court) {
        if (court == null) {
            courtForm.closeForm();
        } else {
            courtForm.openModify(court);
        }
    }

    public void saveEvent(@Nonnull final ManagementFormSaveEvent<Court> managementFormSaveEvent) {
        final Court court = managementFormSaveEvent.getItem();
        courtService.create(court);
        updateCourtList();
    }

    public void updateEvent(@Nonnull final ManagementFormModifyEvent<Court> managementFormModifyEvent) {
        final Court court = managementFormModifyEvent.getItem();
        courtService.update(court);
        updateCourtList();
    }

    public void deleteEvent(@Nonnull final ManagementFormDeleteEvent<Court> managementFormDeleteEvent) {
        final Court court = managementFormDeleteEvent.getItem();
        courtService.delete(court.getId());
        updateCourtList();
    }
}
