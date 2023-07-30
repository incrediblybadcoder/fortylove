package ch.fortylove.presentation.views.management.usermanagement;

import ch.fortylove.configuration.setupdata.data.RoleSetupData;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.components.managementform.FormObserver;
import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Collectors;

@Route(value = "usermanagement", layout = MainLayout.class)
@RolesAllowed(RoleSetupData.ROLE_ADMIN)
public class UserManagementView extends VerticalLayout implements FormObserver<User> {

    @Nonnull private final UserForm userForm;
    @Nonnull private final Grid<User> grid;
    @Nonnull private final TextField filterText = new TextField();
    @Nonnull private final UserService userService;
    @Nonnull private final PasswordEncoder passwordEncoder;

    public UserManagementView(@Nonnull final UserService userService,
                              @Nonnull final UserForm userForm,
                              @Nonnull final PasswordEncoder passwordEncoder) {
        this.userForm = userForm;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        grid = new Grid<>(User.class);

        addClassName("management-view");
        setSizeFull();

        constructUI();
    }

    private void constructUI() {
        configureGrid();
        configureForm();

        final Div content = new Div(grid, userForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateUserList();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();

        grid.addColumn(User::getLastName)
                .setHeader("Nachname")
                .setSortable(true);

        grid.addColumn(User::getFirstName)
                .setHeader("Vorname")
                .setSortable(true);

        grid.addColumn(User::getEmail)
                .setHeader("Email")
                .setSortable(true);

        grid.addColumn(user -> user.getPlayerStatus().getName())
                .setHeader("Status")
                .setSortable(true);

        grid.addColumn(user -> user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(", "))
                )
                .setHeader("Rollen")
                .setSortable(true);

        grid.asSingleSelect().addValueChangeListener(evt -> editUser(evt.getValue()));
    }

    private void configureForm() {
        userForm.addFormObserver(this);
    }

    private void updateUserList() {
        grid.setItems(userService.findAll(filterText.getValue()));
    }

    @Nonnull
    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter nach Name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateUserList());

        final Button addUserButton = new Button(("Benutzer erstellen"), click -> addUser());

        return new HorizontalLayout(filterText, addUserButton);
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        userForm.openCreate();
    }

    private void editUser(@Nonnull final User user) {
        if (user == null) {
            userForm.closeForm();
        } else {
            userForm.openUpdate(user);
        }
    }

    @Override
    public void saveEvent(@Nonnull final User user) {
        user.getAuthenticationDetails().setEncryptedPassword(passwordEncoder.encode("newpassword"));
        userService.create(user);
        updateUserList();
        NotificationUtil.infoNotification("Benutzer wurde erfolgreich angelegt: Passwort = newpassword");
    }

    @Override
    public void updateEvent(@Nonnull final User user) {
        userService.update(user);
        updateUserList();
    }

    @Override
    public void deleteEvent(@Nonnull final User user) {
        if (user.getOwnerBookings().size() == 0 && user.getOpponentBookings().size() == 0) {
            userService.delete(user.getId());
            updateUserList();
            NotificationUtil.infoNotification("Benutzer wurde erfolgreich gelöscht");
        } else {
            NotificationUtil.errorNotification("Benutzer kann nicht gelöscht werden, da er noch Buchungen hat");
        }
    }
}
