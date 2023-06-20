package ch.fortylove.views.membermanagement;

import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.service.RoleService;
import ch.fortylove.persistence.service.UserService;
import ch.fortylove.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route(value = "memberManagement", layout = MainLayout.class)
@CssImport("./styles/shared-styles.css")
@RolesAllowed(Role.ROLE_ADMIN)
public class MemberManagementView extends VerticalLayout {

    private final UserForm form;
    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();
    private final UserService userService;
    private final RoleService roleService;

    Notification notification = new Notification(
            "Besten Dank", 5000);


    public MemberManagementView(UserService userService, final RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
        userService.delete(deleteEvent.getUser());
        updateUserList();
        closeEditor();
    }

    private void saveUser(final UserForm.SaveEvent saveEvent) {
        User user = saveEvent.getUser();
        user.setPassword("newpassword");
        final List<Role> roles = new ArrayList<>();
        final Optional<Role> role = roleService.findByName(Role.ROLE_USER);
        role.ifPresent(roles::add);
        user.setRoles(roles);
        userService.save(saveEvent.getUser());
        updateUserList();
        closeEditor();
        notification.setText("Mitglied wurde erfolgreich angelegt: Passwort = "+user.getPassword());
        notification.setDuration(60000);
        notification.open();
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
        editUser(new User());
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

    private void editUser( User user) {
        if (user == null) {
            closeEditor();
        } else {
            form.setUser(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}
