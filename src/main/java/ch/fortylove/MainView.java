package ch.fortylove;

import ch.fortylove.model.User;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {
//    @Autowired
//    private UserService userService;

    public MainView() {
        List<User> users = new ArrayList<>();
        ListDataProvider<User> dataProvider = new ListDataProvider<>(users);
        Grid<User> grid = new Grid<>(User.class);
        grid.setDataProvider(dataProvider);
//        users.addAll(userService.findAll());

        TextField visitor = new TextField("Your name");

        Button addVisitor = new Button("Ich war hier", e -> {
            User user = new User(visitor.getValue());
            users.add(user);
            dataProvider.refreshAll();
        });

        addVisitor.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addVisitor.addClickShortcut(Key.ENTER);

        addClassName("centered-content");

        add(grid, visitor, addVisitor);
    }
}
