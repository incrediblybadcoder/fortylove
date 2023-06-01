package ch.fortylove.views;

import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.service.UserService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "memberManagement", layout = MainLayout.class)
@RolesAllowed(Role.ROLE_ADMIN)
public class MemberManagementView extends VerticalLayout {

    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();
    private final UserService userService;

    public MemberManagementView(UserService userService) {
        this.userService = userService;
        addClassName("member-management-view");
        setSizeFull();
        configureGrid();
        configureFilter();
        add(filterText, grid);
        updateUserList();

    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateUserList());
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
    }
}
