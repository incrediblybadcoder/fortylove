package ch.fortylove.presentation.views.management.playerstatusmanagement;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.presentation.components.managementform.events.ManagementFormModifyEvent;
import ch.fortylove.presentation.components.managementform.events.ManagementFormSaveEvent;
import ch.fortylove.presentation.views.management.ManagementViewTab;
import ch.fortylove.service.PlayerStatusService;
import ch.fortylove.service.util.DatabaseResult;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.UUID;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PlayerStatusManagementViewTab extends ManagementViewTab {

    @Nonnull private final PlayerStatusService playerStatusService;
    @Nonnull private final PlayerStatusForm playerStatusForm;
    @Nonnull private final NotificationUtil notificationUtil;

    private Grid<PlayerStatus> grid;

    public PlayerStatusManagementViewTab(@Nonnull final PlayerStatusService playerStatusService,
                                         @Nonnull final PlayerStatusForm playerStatusForm,
                                         @Nonnull final NotificationUtil notificationUtil) {
        this.playerStatusService = playerStatusService;
        this.playerStatusForm = playerStatusForm;
        this.notificationUtil = notificationUtil;

        constructUI();
    }

    private void constructUI() {
        configureGrid();
        configureForm();

        final HorizontalLayout content = new HorizontalLayout(grid, playerStatusForm);
        content.addClassName("content");
        content.setSizeFull();

        add(content);
    }

    @Override
    public void refresh() {
        updateCourtList();
    }

    private void configureForm() {
        playerStatusForm.addSaveEventListener(this::saveEvent);
        playerStatusForm.addModifyEventListener(this::updateEvent);
        playerStatusForm.addDeleteEventListenerCustom(this::deleteEvent);
    }

    private void updateCourtList() {
        grid.setItems(playerStatusService.findAll());
    }

    private void configureGrid() {
        grid = new Grid<>(PlayerStatus.class, false);
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        final Grid.Column<PlayerStatus> nameColumn = grid.addColumn(PlayerStatus::getName)
                .setHeader("Name")
                .setSortable(true);

        final Grid.Column<PlayerStatus> bookingsPerDayColumn = grid.addColumn(PlayerStatus::getBookingsPerDay)
                .setHeader("Buchungen pro Tag")
                .setSortable(true);

        final Grid.Column<PlayerStatus> bookableDaysInAdvanceColumn = grid.addColumn(PlayerStatus::getBookableDaysInAdvance)
                .setHeader("Buchbare Tage in die Zukunft")
                .setSortable(true);

        createGridFooter(nameColumn, bookingsPerDayColumn, bookableDaysInAdvanceColumn);

        grid.asSingleSelect().addValueChangeListener(event -> editPlayerStatus(event.getValue()));
    }

    private void createGridFooter(@Nonnull final  Grid.Column<PlayerStatus> nameColumn,
                                  @Nonnull final  Grid.Column<PlayerStatus> bookingsPerDayColumn,
                                  @Nonnull final  Grid.Column<PlayerStatus> bookableDaysInAdvanceColumn) {
        grid.appendFooterRow();
        final FooterRow footerRow = grid.appendFooterRow();
        final FooterRow.FooterCell footerCell = footerRow.join(nameColumn, bookingsPerDayColumn, bookableDaysInAdvanceColumn);

        final Button addButton = new Button("Erstellen", new Icon(VaadinIcon.PLUS_CIRCLE), click -> addPlayerStatus());
        addButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        final HorizontalLayout horizontalLayout = new HorizontalLayout(addButton);
        horizontalLayout.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_20, LumoUtility.Padding.Left.SMALL, LumoUtility.Padding.Right.SMALL);
        footerCell.setComponent(horizontalLayout);
    }

    private void addPlayerStatus() {
        grid.asSingleSelect().clear();
        playerStatusForm.openCreate();
    }

    private void editPlayerStatus(@Nullable final PlayerStatus playerStatus) {
        if (playerStatus == null) {
            playerStatusForm.closeForm();
        } else {
            playerStatusForm.openModify(playerStatus);
        }
    }

    public void saveEvent(@Nonnull final ManagementFormSaveEvent<PlayerStatus> managementFormSaveEvent) {
        final PlayerStatus playerStatus = managementFormSaveEvent.getItem();
        final DatabaseResult<PlayerStatus> playerStatusDatabaseResult = playerStatusService.create(playerStatus);
        notificationUtil.databaseNotification(playerStatusDatabaseResult, String.format("Status %s erstellt", playerStatus.getIdentifier()));
        refresh();
    }

    public void updateEvent(@Nonnull final ManagementFormModifyEvent<PlayerStatus> managementFormModifyEvent) {
        final PlayerStatus playerStatus = managementFormModifyEvent.getItem();
        final DatabaseResult<PlayerStatus> playerStatusDatabaseResult = playerStatusService.update(playerStatus);
        notificationUtil.databaseNotification(playerStatusDatabaseResult, String.format("Status %s gespeichert", playerStatus.getIdentifier()));
        refresh();
    }

    public void deleteEvent(@Nonnull final PlayerStatusFormDeleteEvent playerStatusFormDeleteEvent) {
        final PlayerStatus playerStatusToDelete = playerStatusFormDeleteEvent.getItem();
        final PlayerStatus replacementPlayerStatus = playerStatusFormDeleteEvent.getReplacementPlayerStatus();
        final DatabaseResult<UUID> playerStatusDatabaseResult = playerStatusService.delete(playerStatusToDelete.getId(), replacementPlayerStatus.getId());
        notificationUtil.databaseNotification(playerStatusDatabaseResult, String.format("Status %s gelöscht und mit Status %s ersetzt", playerStatusToDelete.getIdentifier(), replacementPlayerStatus.getIdentifier()));
        refresh();
    }
}
