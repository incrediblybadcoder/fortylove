package ch.fortylove.views.newgrid;

import ch.fortylove.persistence.entity.Court;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.Nonnull;
import java.util.List;

public class OverviewComponent extends VerticalLayout {

    public OverviewComponent() {
        addClassNames(
                LumoUtility.Background.ERROR
        );
        getStyle().set("overflow", "auto");
//        setSpacing(false);
//        setPadding(false);
    }

    public void build(@Nonnull final List<Court> courts) {
        removeAll();

        courts.forEach(court -> add(new CourtComponent(court)));
    }
}
