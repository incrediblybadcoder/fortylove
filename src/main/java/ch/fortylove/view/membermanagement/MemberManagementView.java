package ch.fortylove.view.membermanagement;

import ch.fortylove.configuration.setupdata.data.RoleSetupData;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.service.PlayerStatusService;
import ch.fortylove.service.RoleService;
import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
import ch.fortylove.view.MainLayout;
import ch.fortylove.view.membermanagement.events.DeleteEvent;
import ch.fortylove.view.membermanagement.events.SaveEvent;
import ch.fortylove.view.membermanagement.events.UpdateEvent;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "memberManagement", layout = MainLayout.class)
@RolesAllowed(RoleSetupData.ROLE_ADMIN)
public class MemberManagementView extends VerticalLayout {

    @Nonnull private final UserForm form;
    @Nonnull private final Grid<User> grid = new Grid<>(User.class);
    @Nonnull private final TextField filterText = new TextField();
    @Nonnull private final UserService userService;
    @Nonnull private final PlayerStatusService playerStatusService;
    @Nonnull private final RoleService roleService;
    @Nonnull private final PasswordEncoder passwordEncoder;

    public MemberManagementView(UserService userService, final PlayerStatusService playerStatusService, final RoleService roleService, final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.playerStatusService = playerStatusService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        addClassName("member-management-view");
        setSizeFull();
        configureGrid();

        form = new UserForm(playerStatusService.findAll());
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
            userService.update(updateEvent.getUser());
            updateUserList();
            closeEditor();
    }

    private void deleteUser(final DeleteEvent deleteEvent) {
        Optional<User> userToDelete = userService.findById(deleteEvent.getUser().getId());
        if (userToDelete.isPresent()) {
            User user = userToDelete.get();
            if (user.getOwnerBookings().size() == 0 && user.getOpponentBookings().size() == 0) {
                userService.delete(user.getId());
                updateUserList();
                closeEditor();
                NotificationUtil.infoNotification("Mitglied wurde erfolgreich gelöscht");
            } else {
                NotificationUtil.errorNotification("Mitglied kann nicht gelöscht werden, da es noch Buchungen hat");
            }
        }
    }

    private void saveUser(final SaveEvent saveEvent) {
        final User user = saveEvent.getUser();
        final List<Role> roles = new ArrayList<>();
        final Optional<Role> role = roleService.findByName(RoleSetupData.ROLE_USER);
        role.ifPresent(roles::add);

        final User saveUser = new User(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                passwordEncoder.encode("newpassword"),
                true,
                roles,
                user.getPlayerStatus()
        );

        userService.create(saveUser);
        updateUserList();
        closeEditor();
        NotificationUtil.infoNotification("Mitglied wurde erfolgreich angelegt: Passwort = newpassword");
    }

    private void closeEditor() {
        form.setUser(null);
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
        createNewUser(new User("", "", "", "", true, roleService.getDefaultNewUserRole(), playerStatusService.getDefaultNewUserPlayerStatus()));
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
            // Wenn man einen User im Grid anwählt und einen Eintrag ändert (z.B. Marco auf Carlos)
            // und dann auf Abbrechen klick und dann im Grid wieder den User Marco auswählt,
            // dann wird der User Carlos angezeigt, da der User Carlos noch im Form ist.
            // Daher wird hier der User aus der DB geladen und nicht der User aus dem Grid.
            userService.findById(user.getId()).ifPresent(form::setUser);
            //form.setUser(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}
