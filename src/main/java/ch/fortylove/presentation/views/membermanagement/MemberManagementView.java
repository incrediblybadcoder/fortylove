package ch.fortylove.presentation.views.membermanagement;

import ch.fortylove.configuration.setupdata.data.RoleSetupData;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.factory.UserFactory;
import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.presentation.views.membermanagement.events.DeleteEvent;
import ch.fortylove.presentation.views.membermanagement.events.SaveEvent;
import ch.fortylove.presentation.views.membermanagement.events.UpdateEvent;
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

import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "membermanagement", layout = MainLayout.class)
@RolesAllowed(RoleSetupData.ROLE_ADMIN)
public class MemberManagementView extends VerticalLayout {

    @Nonnull private final UserForm form;
    @Nonnull private final Grid<User> grid;
    @Nonnull private final TextField filterText = new TextField();
    @Nonnull private final UserService userService;
    @Nonnull private final UserFactory userFactory;
    @Nonnull private final PasswordEncoder passwordEncoder;

    public MemberManagementView(@Nonnull final UserService userService,
                                @Nonnull final UserForm form,
                                @Nonnull final UserFactory userFactory,
                                @Nonnull final PasswordEncoder passwordEncoder) {
        this.form = form;
        this.userFactory = userFactory;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        grid = new Grid<>(User.class);

        addClassName("management-view");
        setSizeFull();
        configureGrid();

        form.addSaveListener(this::saveUser);
        form.addUpdateListener(this::updateUser);
        form.addDeleteListener(this::deleteUser);
        form.addCloseListener(e -> closeEditor());

        final Div content = new Div(grid, this.form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateUserList();
        closeEditor();
    }

    private void updateUser(@Nonnull final UpdateEvent updateEvent) {
        userService.update(updateEvent.getUser());
        updateUserList();
        closeEditor();
    }

    private void deleteUser(@Nonnull final DeleteEvent deleteEvent) {
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

    private void saveUser(@Nonnull final SaveEvent saveEvent) {
        final User user = saveEvent.getUser();
        user.getAuthenticationDetails().setEncryptedPassword(passwordEncoder.encode("newpassword"));
        userService.create(user);
        updateUserList();
        closeEditor();
        NotificationUtil.infoNotification("Mitglied wurde erfolgreich angelegt: Passwort = newpassword");
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
    }

    @Nonnull
    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter nach Name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateUserList());

        final Button addUserButton = new Button(("Mitglied erstellen"), click -> addUser());

        return new HorizontalLayout(filterText, addUserButton);
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        createNewUser(userFactory.newEmptyDefaultUser());
    }

    private void createNewUser(@Nonnull final User user) {
        if (user == null) {
            closeEditor();
        } else {
            form.createUserForm();
            form.setUser(user);
            form.setVisible(true);
        }
    }

    private void updateUserList() {
        grid.setItems(userService.findAll(filterText.getValue()));
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();

        grid.addColumn(User::getLastName)
                .setHeader("Nachname")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(User::getFirstName)
                .setHeader("Vorname")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(User::getEmail)
                .setHeader("Email")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(user -> user.getPlayerStatus().getName())
                .setHeader("Spieler Status")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(user -> user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(", "))
                )
                .setHeader("Roles")
                .setSortable(true)
                .setAutoWidth(true);

        grid.asSingleSelect().addValueChangeListener(evt -> editUser(evt.getValue()));
    }

    private void editUser(@Nonnull final User user) {
        if (user == null) {
            closeEditor();
        } else {
            form.updateUserForm();
            form.setUser(user);
            form.setVisible(true);
        }
    }
}
