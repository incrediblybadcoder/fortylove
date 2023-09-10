package ch.fortylove.presentation.views.management.usermanagement;

import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.UserStatus;
import ch.fortylove.presentation.components.managementform.events.ManagementFormDeleteEvent;
import ch.fortylove.presentation.components.managementform.events.ManagementFormModifyEvent;
import ch.fortylove.presentation.components.managementform.events.ManagementFormSaveEvent;
import ch.fortylove.presentation.views.management.ManagementViewTab;
import ch.fortylove.presentation.views.management.usermanagement.admissiondialog.AcceptAdmissionDialogEvent;
import ch.fortylove.presentation.views.management.usermanagement.admissiondialog.AdmissionDialog;
import ch.fortylove.presentation.views.management.usermanagement.admissiondialog.RejectAdmissionDialogEvent;
import ch.fortylove.service.UserService;
import ch.fortylove.service.util.DatabaseResult;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserManagementViewTab extends ManagementViewTab {

    @Nonnull private final UserService userService;
    @Nonnull private final NotificationUtil notificationUtil;
    @Nonnull private final AdmissionDialog admissionDialog;
    @Nonnull private final UserForm userForm;

    @Nonnull private final Grid<User> grid = new Grid<>(User.class, false);
    @Nonnull final private UserFilter userFilter = new UserFilter();

    @Autowired
    public UserManagementViewTab(@Nonnull final UserService userService,
                                 @Nonnull final NotificationUtil notificationUtil,
                                 @Nonnull final AdmissionDialog admissionDialog,
                                 @Nonnull final UserForm userForm) {
        super(VaadinIcon.USERS.create(), "Benutzer");
        this.notificationUtil = notificationUtil;
        this.admissionDialog = admissionDialog;
        this.userForm = userForm;
        this.userService = userService;

        constructUI();
    }

    private void constructUI() {
        configureGrid();
        configureForm();
        configureDialog();

        final HorizontalLayout content = new HorizontalLayout(grid, userForm);
        content.addClassName("content");
        content.setSizeFull();

        add(content);
    }

    private void configureDialog() {
        admissionDialog.addAcceptAdmissionDialogListener(this::acceptAdmissionDialogEvent);
        admissionDialog.addRejectAdmissionDialogListener(this::rejectAdmissionDialogEvent);
    }

    @Override
    public void refresh() {
        updateUserList();
    }

    private void configureForm() {
        userForm.addSaveEventListener(this::saveEvent);
        userForm.addModifyEventListener(this::updateEvent);
        userForm.addDeleteEventListener(this::deleteEvent);
    }

    private void updateUserList() {
        List<User> allVisibleUsers = userService.getAllVisibleUsers();

        final GridListDataView<User> userGridListDataView = grid.setItems(allVisibleUsers);
        userFilter.setDataView(userGridListDataView);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        final Grid.Column<User> lastNameColumn = grid.addColumn(User::getLastName)
                .setHeader("Nachname")
                .setSortable(true);

        final Grid.Column<User> firstNameColumn = grid.addColumn(User::getFirstName)
                .setHeader("Vorname")
                .setSortable(true);

        final Grid.Column<User> emailColumn = grid.addColumn(User::getEmail)
                .setHeader("Email")
                .setSortable(true);

        final Grid.Column<User> userStatusColumn = grid.addColumn(new ComponentRenderer<>(user -> {
                    final UserStatus userStatus = user.getUserStatus();
                    final boolean isGuestPendingStatus = userStatus.equals(UserStatus.GUEST_PENDING);
                    final String displayName = isGuestPendingStatus ? UserStatus.GUEST.getIdentifier() : userStatus.getIdentifier();

                    if (isGuestPendingStatus) {
                        final Button admissionButton = new Button("Anfrage");
                        admissionButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
                        admissionButton.addClickListener(handleAdmission(user));

                        final HorizontalLayout userStatusLayout = new HorizontalLayout(new Span(displayName), admissionButton);
                        userStatusLayout.setAlignItems(Alignment.CENTER);
                        return userStatusLayout;
                    }
                    return new Span(displayName);
                }))
                .setHeader("Benutzerstatus")
                .setSortable(true);

        final Grid.Column<User> playerStatusColumn = grid.addColumn(user -> user.getPlayerStatus().getIdentifier())
                .setHeader("Spielerstatus")
                .setSortable(true);

        final Grid.Column<User> roleColumn = grid.addColumn(user -> user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(", "))
                )
                .setHeader("Rollen")
                .setSortable(true);

        createGridHeader(lastNameColumn, firstNameColumn, emailColumn, userStatusColumn, playerStatusColumn, roleColumn);
        createGridFooter(lastNameColumn, firstNameColumn, emailColumn, userStatusColumn, playerStatusColumn, roleColumn);

        grid.asSingleSelect().addValueChangeListener(evt -> editUser(evt.getValue()));
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> handleAdmission(@Nonnull final User user) {
        return event -> {
            userForm.closeForm();
            admissionDialog.open(user);
        };
    }

    private void createGridHeader(@Nonnull final Grid.Column<User> lastNameColumn,
                                  @Nonnull final Grid.Column<User> firstNameColumn,
                                  @Nonnull final Grid.Column<User> emailColumn,
                                  @Nonnull final Grid.Column<User> userStatusColumn,
                                  @Nonnull final Grid.Column<User> playerStatusColumn,
                                  @Nonnull final Grid.Column<User> roleColumn) {
        final HeaderRow headerRow = grid.appendHeaderRow();
        headerRow.getCell(lastNameColumn).setComponent(createFilterHeader(userFilter::setLastName));
        headerRow.getCell(firstNameColumn).setComponent(createFilterHeader(userFilter::setFirstName));
        headerRow.getCell(emailColumn).setComponent(createFilterHeader(userFilter::setEmail));
        headerRow.getCell(userStatusColumn).setComponent(createFilterHeader(userFilter::setUserStatus));
        headerRow.getCell(playerStatusColumn).setComponent(createFilterHeader(userFilter::setPlayerStatus));
        headerRow.getCell(roleColumn).setComponent(createFilterHeader(userFilter::setRoles));
    }

    @Nonnull
    private TextField createFilterHeader(@Nonnull final Consumer<String> filterChangeConsumer) {
        final TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.addValueChangeListener(e -> filterChangeConsumer.accept(e.getValue()));

        return textField;
    }

    private void createGridFooter(@Nonnull final Grid.Column<User> lastNameColumn,
                                  @Nonnull final Grid.Column<User> firstNameColumn,
                                  @Nonnull final Grid.Column<User> emailColumn,
                                  @Nonnull final Grid.Column<User> userStatusColumn,
                                  @Nonnull final Grid.Column<User> playerStatusColumn,
                                  @Nonnull final Grid.Column<User> roleColumn) {
        grid.appendFooterRow();
        final FooterRow footerRow = grid.appendFooterRow();
        final FooterRow.FooterCell footerCell = footerRow.join(lastNameColumn, firstNameColumn, emailColumn, userStatusColumn, playerStatusColumn, roleColumn);

        final Button addButton = new Button("Erstellen", new Icon(VaadinIcon.PLUS_CIRCLE), click -> addUser());
        addButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        final HorizontalLayout horizontalLayout = new HorizontalLayout(addButton);
        horizontalLayout.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_20, LumoUtility.Padding.Left.SMALL, LumoUtility.Padding.Right.SMALL);
        footerCell.setComponent(horizontalLayout);
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        userForm.openCreate();
    }

    private void editUser(@Nullable final User user) {
        if (user == null) {
            userForm.closeForm();
        } else {
            userForm.openModify(user);
        }
    }

    public void saveEvent(@Nonnull final ManagementFormSaveEvent<User> managementFormSaveEvent) {
        final User user = managementFormSaveEvent.getItem();
        final DatabaseResult<User> userDatabaseResult = userService.create(user);
        userService.generateAndSaveResetToken(user.getEmail(), 10000);
        notificationUtil.databaseNotification(userDatabaseResult);
        notificationUtil.persistentInformationNotification(String.format("Der Benutzer %s wurde erstellt.\n Ein Link um das Passwort zu ändern wurde an %s gesendet.", user.getIdentifier(), user.getEmail()));
        refresh();
    }

    public void updateEvent(@Nonnull final ManagementFormModifyEvent<User> managementFormModifyEvent) {
        final User user = managementFormModifyEvent.getItem();
        final DatabaseResult<User> userDatabaseResult = userService.update(user);
        notificationUtil.databaseNotification(userDatabaseResult);
        refresh();
    }

    public void deleteEvent(@Nonnull final ManagementFormDeleteEvent<User> managementFormDeleteEvent) {
        final User user = managementFormDeleteEvent.getItem();
        final DatabaseResult<UUID> userDatabaseResult = userService.delete(user.getId());
        notificationUtil.databaseNotification(userDatabaseResult);
        refresh();
    }

    private void acceptAdmissionDialogEvent(@Nonnull final AcceptAdmissionDialogEvent acceptAdmissionDialogEvent) {
        final User user = acceptAdmissionDialogEvent.getUser();
        final DatabaseResult<User> result = userService.changeUserStatusToMember(user, acceptAdmissionDialogEvent.getPlayerStatus());
        notificationUtil.databaseNotification(result);
        refresh();
    }

    private void rejectAdmissionDialogEvent(@Nonnull final RejectAdmissionDialogEvent rejectAdmissionDialogEvent) {
        final User user = rejectAdmissionDialogEvent.getUser();
        final DatabaseResult<User> result = userService.changeUserStatusToGuest(user);
        notificationUtil.databaseNotification(result);
        refresh();
    }
}
