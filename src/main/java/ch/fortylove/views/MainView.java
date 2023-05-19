package ch.fortylove.views;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.service.UserService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * The main view contains a button and a click listener.
 */
@Route(value = "", layout = MainLayout.class)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@PageTitle("Main")
@PermitAll
public class MainView extends VerticalLayout {

    @Autowired private UserService userService;

    public MainView() {
        final List<User> usersDisplay = new ArrayList<>();
        final ListDataProvider<User> dataProvider = new ListDataProvider<>(usersDisplay);
        final Grid<User> grid = new Grid<>(User.class);
        grid.setDataProvider(dataProvider);
        grid.setColumns("email");
        grid.getColumnByKey("email").setHeader("Wer war hier?");
        grid.setHeight("300px");

        final TextField visitor = new TextField("Your email");

        final Button addVisitor = new Button("Ich war hier!", e -> {
//            usersDisplay.clear();
//            User user = new User(visitor.getValue());
//            userService.create(user);
//            usersDisplay.addAll(userService.findAll());
//            dataProvider.refreshAll();
        });

        addVisitor.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addVisitor.addClickShortcut(Key.ENTER);

        addClassName("centered-content");

        add(grid, visitor, addVisitor);
    }
}
