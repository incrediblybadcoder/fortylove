package ch.fortylove.views;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.service.CourtService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

/**
 * Main view
 */
@Route(value = "", layout = MainLayout.class)
@PageTitle("Main")
@PermitAll
public class MainView extends VerticalLayout {

    private final CourtService courtService;
    private Grid<Court> grid;

    @Autowired
    public MainView(@Nonnull final CourtService courtService) {
        super();
        this.courtService = courtService;

        initView();
    }

    private void initView() {
        configureGrid();
        updateGrid();
    }

    private void configureGrid() {
        grid = new Grid<>(Court.class, false);
        grid.addColumn(Court::getId).setHeader("Court ID");
        grid.setHeight("300px");
        add(grid);
    }

    private void updateGrid() {
        grid.setItems(courtService.findAll());
    }
}
