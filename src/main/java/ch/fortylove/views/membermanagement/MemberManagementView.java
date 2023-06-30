package ch.fortylove.views.membermanagement;

import ch.fortylove.persistence.dto.RoleDTO;
import ch.fortylove.persistence.dto.UserDTO;
import ch.fortylove.persistence.entity.Role;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route(value = "memberManagement", layout = MainLayout.class)
@RolesAllowed(Role.ROLE_ADMIN)
public class MemberManagementView extends VerticalLayout {

    private final UserForm form;
    Grid<UserDTO> grid = new Grid<>(UserDTO.class);
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
        final UserDTO userDTO = saveEvent.getUser();
        final List<RoleDTO> roleDTOs = new ArrayList<>();
        final Optional<RoleDTO> role = roleService.findByName(Role.ROLE_USER);
        role.ifPresent(roleDTOs::add);
        final UserDTO saveUserDTO = new UserDTO(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), "newpassword", true, roleDTOs, null);
        userService.save(saveUserDTO);
        updateUserList();
        closeEditor();
        notification.setText("Mitglied wurde erfolgreich angelegt: Passwort = " + saveUserDTO.getPassword());
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
        editUser(new UserDTO(0L, "", "", "", "", false, null, null));
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

    private void editUser(UserDTO userDTO) {
        if (userDTO == null) {
            closeEditor();
        } else {
            form.setUser(userDTO);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}
