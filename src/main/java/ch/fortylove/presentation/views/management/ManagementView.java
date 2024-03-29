package ch.fortylove.presentation.views.management;

import ch.fortylove.configuration.setupdata.data.RoleSetupData;
import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.presentation.views.management.courtmanagement.CourtManagementViewTab;
import ch.fortylove.presentation.views.management.playerstatusmanagement.PlayerStatusManagementViewTab;
import ch.fortylove.presentation.views.management.usermanagement.UserManagementViewTab;
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

    public ManagementView(@Nonnull final CourtManagementViewTab courtManagementViewTab,
                          @Nonnull final PlayerStatusManagementViewTab playerStatusManagementViewTab,
                          @Nonnull final UserManagementViewTab userManagementViewTab) {
        addClassName("management-view");
        setSizeFull();

        constructUI(courtManagementViewTab, playerStatusManagementViewTab, userManagementViewTab);
    }

    private void constructUI(@Nonnull final CourtManagementViewTab courtManagementViewTab,
                             @Nonnull final PlayerStatusManagementViewTab playerStatusManagementViewTab,
                             @Nonnull final UserManagementViewTab userManagementViewTab) {
        final Map<Tab, ManagementViewTab> tabs = new LinkedHashMap<>();
        tabs.put(courtManagementViewTab.createTab(), courtManagementViewTab);
        tabs.put(playerStatusManagementViewTab.createTab(), playerStatusManagementViewTab);
        tabs.put(userManagementViewTab.createTab(), userManagementViewTab);

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
}
