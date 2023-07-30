package ch.fortylove.presentation.views.courtmanagement;

import ch.fortylove.configuration.setupdata.data.RoleSetupData;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtIcon;
import ch.fortylove.persistence.entity.CourtType;
import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.presentation.views.courtmanagement.events.DeleteEvent;
import ch.fortylove.presentation.views.courtmanagement.events.SaveEvent;
import ch.fortylove.presentation.views.courtmanagement.events.UpdateEvent;
import ch.fortylove.service.CourtService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "courtmanagement", layout = MainLayout.class)
@RolesAllowed(RoleSetupData.ROLE_ADMIN)
public class CourtManagementView extends VerticalLayout {

    @Nonnull private final CourtService courtService;

    @Nonnull private final Grid<Court> grid;
    @Nonnull private final CourtForm courtForm;

    public CourtManagementView(@Nonnull final CourtService courtService,
                               @Nonnull final CourtForm courtForm) {
        this.courtService = courtService;
        this.courtForm = courtForm;
        grid = new Grid<>(Court.class, false);

        addClassName("management-view");
        setSizeFull();

        constructUI();
    }

    private void constructUI() {
        configureGrid();
        configureForm();

        final Div content = new Div(grid, courtForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        closeEditor();
        updateCourtList();
    }

    private void configureForm() {
        courtForm.addSaveListener(this::saveCourt);
        courtForm.addUpdateListener(this::updateCourt);
        courtForm.addDeleteListener(this::deleteCourt);
        courtForm.addCloseListener(e -> closeEditor());
    }

    private void saveCourt(@Nonnull final SaveEvent saveEvent) {
        courtService.create(saveEvent.getCourt());
        updateCourtList();
        closeEditor();
    }

    private void deleteCourt(@Nonnull final DeleteEvent deleteEvent) {
        courtService.delete(deleteEvent.getCourt().getId());
        updateCourtList();
        closeEditor();
    }

    private void updateCourt(@Nonnull final UpdateEvent updateEvent) {
        courtService.update(updateEvent.getCourt());
        updateCourtList();
        closeEditor();
    }

    private void closeEditor() {
        courtForm.setCourt(null);
        courtForm.setVisible(false);
    }

    private void updateCourtList() {
        grid.setItems(courtService.findAll());
    }

    @Nonnull
    private HorizontalLayout getToolBar() {
        final Button addCourtButton = new Button(("Court erstellen"), click -> addCourt());

        final HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.add(addCourtButton);
        return toolbar;
    }

    private void configureGrid() {
        grid.setSizeFull();

        grid.addColumn(Court::getNumber)
                .setHeader("Nummer")
                .setSortable(true);

        grid.addComponentColumn(court -> getIconComponent(court.getCourtIcon()))
                .setHeader("Icon")
                .setSortable(true);

        grid.addColumn(court -> court.getCourtType().getMaterial())
                .setHeader("Material")
                .setSortable(true);

        grid.addColumn(Court::getName)
                .setHeader("Name")
                .setSortable(true);

        grid.asSingleSelect().addValueChangeListener(event -> editCourt(event.getValue()));
    }

    @Nonnull
    private VerticalLayout getIconComponent(@Nonnull final CourtIcon courtIcon) {
        final VerticalLayout verticalLayout = new VerticalLayout();
        final Image icon = new Image(courtIcon.getResource(), courtIcon.getCode());
        icon.setHeight("1em");
        verticalLayout.add(icon);
        return verticalLayout;
    }

    private void addCourt() {
        grid.asSingleSelect().clear();

        final Court court = new Court(CourtType.CLAY, CourtIcon.ORANGE, 0, "");
        courtForm.setCourt(court);
        courtForm.openNewCourt();
    }

    private void editCourt(@Nonnull final Court court) {
        if (court == null) {
            closeEditor();
        } else {
            courtForm.setCourt(court);
            courtForm.openEditCourt();
        }
    }
}
