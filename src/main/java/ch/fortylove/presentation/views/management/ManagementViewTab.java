package ch.fortylove.presentation.views.management;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public abstract class ManagementViewTab extends VerticalLayout implements Refreshable {

    public ManagementViewTab() {
        setSizeFull();
        setPadding(false);
        addClassName(LumoUtility.Padding.Top.MEDIUM);
    }
}
