package ch.fortylove.presentation.views.management.usermanagement;

import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.components.managementform.events.ManagementFormDeleteEvent;
import ch.fortylove.presentation.components.managementform.events.ManagementFormModifyEvent;
import ch.fortylove.presentation.components.managementform.events.ManagementFormSaveEvent;
import ch.fortylove.presentation.views.management.ManagementViewTab;
import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserManagementView extends ManagementViewTab {

    @Nonnull private final UserService userService;

    @Nonnull private final UserForm userForm;
    @Nonnull private final PasswordEncoder passwordEncoder;

    private Grid<User> grid;
    private UserFilter userFilter;

    public UserManagementView(@Nonnull final UserService userService,
                              @Nonnull final UserForm userForm,
                              @Nonnull final PasswordEncoder passwordEncoder) {
        this.userForm = userForm;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;

        setSizeFull();
        setPadding(false);
        addClassName(LumoUtility.Padding.Top.MEDIUM);

        constructUI();
    }

    private void constructUI() {
        configureGrid();
        configureForm();

        final HorizontalLayout content = new HorizontalLayout(grid, userForm);
        content.addClassName("content");
        content.setSizeFull();

        add(content);
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
        grid = new Grid<>(User.class, false);
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

        final Grid.Column<User> playerStatusColumn = grid.addColumn(user -> user.getPlayerStatus().getName())
                .setHeader("Status")
                .setSortable(true);

        final Grid.Column<User> roleColumn = grid.addColumn(user -> user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(", "))
                )
                .setHeader("Rollen")
                .setSortable(true);

        createGridHeader(lastNameColumn, firstNameColumn, emailColumn, playerStatusColumn, roleColumn);
        createGridFooter(lastNameColumn, firstNameColumn, emailColumn, playerStatusColumn, roleColumn);

        grid.asSingleSelect().addValueChangeListener(evt -> editUser(evt.getValue()));
    }

    private void createGridHeader(@Nonnull final Grid.Column<User> lastNameColumn,
                                  @Nonnull final Grid.Column<User> firstNameColumn,
                                  @Nonnull final Grid.Column<User> emailColumn,
                                  @Nonnull final Grid.Column<User> playerStatusColumn,
                                  @Nonnull final Grid.Column<User> roleColumn) {
        userFilter = new UserFilter();
        final HeaderRow headerRow = grid.appendHeaderRow();
        headerRow.getCell(lastNameColumn).setComponent(createFilterHeader(userFilter::setLastName));
        headerRow.getCell(firstNameColumn).setComponent(createFilterHeader(userFilter::setFirstName));
        headerRow.getCell(emailColumn).setComponent(createFilterHeader(userFilter::setEmail));
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
                                  @Nonnull final Grid.Column<User> playerStatusColumn,
                                  @Nonnull final Grid.Column<User> roleColumn) {
        grid.appendFooterRow();
        final FooterRow footerRow = grid.appendFooterRow();
        final FooterRow.FooterCell footerCell = footerRow.join(lastNameColumn, firstNameColumn, emailColumn, playerStatusColumn, roleColumn);

        final Button addButton = new Button("Erstellen", new Icon(VaadinIcon.PLUS_CIRCLE),click -> addUser());
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
        user.getAuthenticationDetails().setEncryptedPassword(passwordEncoder.encode("newpassword"));
        userService.create(user);
        refresh();
        NotificationUtil.persistentInformationNotification("Benutzer wurde erfolgreich angelegt:\nPasswort = newpassword");
    }

    public void updateEvent(@Nonnull final ManagementFormModifyEvent<User> managementFormModifyEvent) {
        final User user = managementFormModifyEvent.getItem();
        userService.update(user);
        refresh();
    }

    public void deleteEvent(@Nonnull final ManagementFormDeleteEvent<User> managementFormDeleteEvent) {
        final User user = managementFormDeleteEvent.getItem();
        if (user.getOwnerBookings().size() == 0 && user.getOpponentBookings().size() == 0) {
            userService.delete(user.getId());
            refresh();
            NotificationUtil.informationNotification("Benutzer wurde erfolgreich gelöscht");
        } else {
            NotificationUtil.errorNotification("Benutzer kann nicht gelöscht werden, da er noch Buchungen hat");
        }
    }
}
