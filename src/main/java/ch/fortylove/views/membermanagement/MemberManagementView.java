package ch.fortylove.views.membermanagement;

import ch.fortylove.persistence.dto.Role;
import ch.fortylove.persistence.dto.User;
import ch.fortylove.persistence.dto.UserFormInformations;
import ch.fortylove.persistence.entity.RoleEntity;
import ch.fortylove.persistence.service.RoleService;
import ch.fortylove.persistence.service.UserService;
import ch.fortylove.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route(value = "memberManagement", layout = MainLayout.class)
@RolesAllowed(RoleEntity.ROLE_ADMIN)
public class MemberManagementView extends VerticalLayout {

    private final UserForm form;
    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();
    private final UserService userService;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    Notification notification = new Notification(
            "Besten Dank", 5000);


    public MemberManagementView(UserService userService, final RoleService roleService, final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        addClassName("member-management-view");
        setSizeFull();
        configureGrid();

        form = new UserForm();
        form.addSaveListener(this::saveUser);
        form.addDeleteListener(this::deleteUser);
        form.addCloseListener(e -> closeEditor());
        

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();


        add(getToolBar(), content);
        updateUserList();
        closeEditor();

    }

    private void deleteUser(final UserForm.DeleteEvent deleteEvent) {
        //userService.delete(deleteEvent.getUser());
        updateUserList();
        closeEditor();
    }

    private void saveUser(final UserForm.SaveEvent saveEvent) {
        //final User user = saveEvent.getUser();
        final UserFormInformations userFormInformations = saveEvent.getUser();
        final String emailAliasPrimaryKey = userFormInformations.getEmail();
        final Optional<User> user = userService.findByEmail(emailAliasPrimaryKey);
        if (user.isPresent()){
            userService.updateUser(saveEvent.getUser());
            notification.setText("Mitglied update durchgeführt");
            notification.setDuration(3000);
            notification.open();
        }
        else {
            final List<Role> roles = new ArrayList<>();
            final Optional<Role> role = roleService.findByName(RoleEntity.ROLE_USER);
            role.ifPresent(roles::add);
            final User saveUser = new User(99L, userFormInformations.getFirstName(), userFormInformations.getLastName(), userFormInformations.getEmail(), passwordEncoder.encode("newpassword"), true, roles, null);
            userService.save(saveUser);
            notification.setText("Mitglied wurde erfolgreich angelegt: Passwort = newpassword");
            notification.setDuration(60000);
            notification.open();
        }
        updateUserList();
        closeEditor();
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
        editUser(new User(0L, "", "", "", "", false, null, null));
    }

    private void updateUserList() {
        grid.setItems(userService.findAll(filterText.getValue()));
    }

    private void configureGrid() {
        grid.addClassName("member-grid");
        grid.setSizeFull();
        //ToDO: add role column
        grid.setColumns("firstName", "lastName", "email");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editUser(evt.getValue()));
    }

    private void editUser(User user) {
        if (user == null) {
            closeEditor();
        } else {
            form.setUser(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}
