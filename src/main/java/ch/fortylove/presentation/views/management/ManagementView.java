package ch.fortylove.presentation.views.management;

import ch.fortylove.configuration.setupdata.data.RoleSetupData;
import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.presentation.views.management.courtmanagement.CourtManagementView;
import ch.fortylove.presentation.views.management.playerstatusmanagement.PlayerStatusManagementView;
import ch.fortylove.presentation.views.management.usermanagement.UserManagementView;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.RolesAllowed;

import java.util.LinkedHashMap;
import java.util.Map;

@Route(value = ManagementView.ROUTE, layout = MainLayout.class)
@PageTitle(ManagementView.PAGE_TITLE)
@RolesAllowed({RoleSetupData.ROLE_ADMIN, RoleSetupData.ROLE_STAFF})
public class ManagementView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "management";
    @Nonnull public static final String PAGE_TITLE = "Verwaltung";

    public ManagementView(@Nonnull final CourtManagementView courtManagementView,
                          @Nonnull final PlayerStatusManagementView playerStatusManagementView,
                          @Nonnull final UserManagementView userManagementView) {
        addClassName("management-view");
        setSizeFull();

        constructUI(courtManagementView, playerStatusManagementView, userManagementView);
    }

    private void constructUI(@Nonnull final CourtManagementView courtManagementView,
                             @Nonnull final PlayerStatusManagementView playerStatusManagementView,
                             @Nonnull final UserManagementView userManagementView) {
        final Map<Tab, ManagementViewTab> tabs = new LinkedHashMap<>();
        tabs.put(createTab(VaadinIcon.LIST_OL.create(), "Plätze"), courtManagementView);
        tabs.put(createTab(VaadinIcon.STAR.create(), "Status"), playerStatusManagementView);
        tabs.put(createTab(VaadinIcon.USERS.create(), "Benutzer"), userManagementView);

        final TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.addSelectedChangeListener(selectedChangeEvent-> {
            final Tab selectedTab = selectedChangeEvent.getSelectedTab();
            final ManagementViewTab managementViewTab = tabs.get(selectedTab);
            managementViewTab.refresh();
        });
        tabs.forEach(tabSheet::add);

        add(tabSheet);
    }

    @Nonnull
    private Tab createTab(@Nonnull final Icon icon,
                          @Nonnull final String label) {
        return new Tab(icon, new Span(label));
    }
}
