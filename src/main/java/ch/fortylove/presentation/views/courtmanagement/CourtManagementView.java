package ch.fortylove.presentation.views.courtmanagement;

import ch.fortylove.configuration.setupdata.data.RoleSetupData;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtIcon;
import ch.fortylove.presentation.components.managementform.FormObserver;
import ch.fortylove.presentation.views.MainLayout;
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
public class CourtManagementView extends VerticalLayout implements FormObserver<Court> {

    @Nonnull private final CourtService courtService;
    @Nonnull private final CourtForm courtForm;

    @Nonnull private final Grid<Court> grid;

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
        updateCourtList();
    }

    private void configureForm() {
        courtForm.addFormObserver(this);
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
        courtForm.openCreate();
    }

    private void editCourt(@Nonnull final Court court) {
        if (court == null) {
            courtForm.closeForm();
        } else {
            courtForm.openUpdate(court);
        }
    }

    @Override
    public void saveEvent(@Nonnull final Court court) {
        courtService.create(court);
        updateCourtList();
    }

    @Override
    public void updateEvent(@Nonnull final Court court) {
        courtService.update(court);
        updateCourtList();
    }

    @Override
    public void deleteEvent(@Nonnull final Court court) {
        courtService.delete(court.getId());
        updateCourtList();
    }
}
