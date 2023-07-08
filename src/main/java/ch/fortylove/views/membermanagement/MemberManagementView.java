package ch.fortylove.views.membermanagement;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.service.PlayerStatusService;
import ch.fortylove.persistence.service.RoleService;
import ch.fortylove.persistence.service.UserService;
import ch.fortylove.views.MainLayout;
import ch.fortylove.views.membermanagement.dto.UserFormInformations;
import ch.fortylove.views.membermanagement.events.DeleteEvent;
import ch.fortylove.views.membermanagement.events.SaveEvent;
import ch.fortylove.views.membermanagement.events.UpdateEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "memberManagement", layout = MainLayout.class)
@RolesAllowed(Role.ROLE_ADMIN)
public class MemberManagementView extends VerticalLayout {

    @Nonnull private final UserForm form;
    @Nonnull private final Grid<User> grid = new Grid<>(User.class);
    @Nonnull private final TextField filterText = new TextField();
    @Nonnull private final UserService userService;
    @Nonnull private final PlayerStatusService playerStatusService;
    @Nonnull private final RoleService roleService;
    @Nonnull private final PasswordEncoder passwordEncoder;

    Notification notification = new Notification(
            "Besten Dank", 5000);


    public MemberManagementView(UserService userService, final PlayerStatusService playerStatusService, final RoleService roleService, final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.playerStatusService = playerStatusService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        addClassName("member-management-view");
        setSizeFull();
        configureGrid();

        form = new UserForm();
        form.addSaveListener(this::saveUser);
        form.addUpdateListener(this::updateUser);
        form.addDeleteListener(this::deleteUser);
        form.addCloseListener(e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateUserList();
        closeEditor();
    }

    private void updateUser(final UpdateEvent updateEvent) {
        UserFormInformations userFormInformations = updateEvent.getUser();
        Optional<User> userToUpdate = userService.findById(userFormInformations.getId());
        if (userToUpdate.isPresent()) {
            User user = userToUpdate.get();
            user.setFirstName(userFormInformations.getFirstName());
            user.setLastName(userFormInformations.getLastName());
            user.setEmail(userFormInformations.getEmail());
            userService.update(user);
            updateUserList();
            closeEditor();
        }
    }

    private void deleteUser(final DeleteEvent deleteEvent) {
        UserFormInformations userFormInformations = deleteEvent.getUser();
        Optional<User> userToDelete = userService.findById(userFormInformations.getId());
        if (userToDelete.isPresent()) {
            User user = userToDelete.get();
            if (user.getOwnerBookings().size() == 0 && user.getOpponentBookings().size() == 0) {
                userService.delete(user.getId());
                updateUserList();
                closeEditor();
                notification.setText("Mitglied wurde erfolgreich gelöscht");
                notification.setDuration(10000);
                notification.open();
            } else {
                notification.setText("Mitglied kann nicht gelöscht werden, da es noch Buchungen hat");
                notification.setDuration(10000);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            }
        }
    }

    private void saveUser(final SaveEvent saveEvent) {
        final UserFormInformations userFormInformations = saveEvent.getUser();
        final List<Role> roles = new ArrayList<>();
        final Optional<Role> role = roleService.findByName(Role.ROLE_USER);
        role.ifPresent(roles::add);
        final Optional<PlayerStatus> playerStatus = playerStatusService.findByName("aktiv");
        if (playerStatus.isEmpty()) {
            throw new RuntimeException("PlayerStatus aktiv not found");
        }

        final User saveUser = new User(userFormInformations.getFirstName(),
                userFormInformations.getLastName(),
                userFormInformations.getEmail(),
                passwordEncoder.encode("newpassword"),
                true,
                roles,
                null,
                null,
                playerStatus.get());
        userService.create(saveUser);
        updateUserList();
        closeEditor();
        notification.setText("Mitglied wurde erfolgreich angelegt: Passwort = newpassword");
        notification.setDuration(5000);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();
    }

    private void closeEditor() {
        //form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter nach Name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateUserList());

        Button addUserButton = new Button(("Mitglied anlegen"), click -> addUser());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addUserButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        createNewUser(new User("", "", "", "", false, null, null, null, null));
    }

    private void createNewUser(final User user) {
        if (user == null) {
            closeEditor();
        } else {
            form.createUserForm();
            form.setUser(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateUserList() {
        grid.setItems(userService.findAll(filterText.getValue()));
    }

    private void configureGrid() {
        grid.addClassName("member-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(User::getId).setHeader("ID");
        grid.addColumn(user -> user.getPlayerStatus().getName()).setHeader("Spieler Status");
        grid.addColumn(User::getFirstName).setHeader("Vorname");
        grid.addColumn(User::getLastName).setHeader("Nachname");
        grid.addColumn(User::getEmail).setHeader("Email");
        grid.addColumn(user -> user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(", "))
                )
                .setHeader("Roles");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editUser(evt.getValue()));
    }

    private void editUser(User user) {
        if (user == null) {
            closeEditor();
        } else {
            form.updateUserForm();
            form.setUser(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}
