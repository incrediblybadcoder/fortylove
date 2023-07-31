package ch.fortylove.presentation.views.management;

import ch.fortylove.configuration.setupdata.data.RoleSetupData;
import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.presentation.views.management.courtmanagement.CourtManagementView;
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

@Route(value = ManagementView.ROUTE, layout = MainLayout.class)
@PageTitle(ManagementView.PAGE_TITLE)
@RolesAllowed({RoleSetupData.ROLE_ADMIN, RoleSetupData.ROLE_STAFF})
public class ManagementView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "management";
    @Nonnull public static final String PAGE_TITLE = "Verwaltung";

    @Nonnull private final CourtManagementView courtManagementView;
    @Nonnull private final UserManagementView userManagementView;

    public ManagementView(@Nonnull final CourtManagementView courtManagementView,
                          @Nonnull final UserManagementView userManagementView) {
        this.courtManagementView = courtManagementView;
        this.userManagementView = userManagementView;

        addClassName("management-view");
        setSizeFull();

        constructUI();
    }

    private void constructUI() {
        final Tab courtManagement = createTab(VaadinIcon.LIST_OL.create(), "Plätze");
        final Tab userManagement = createTab(VaadinIcon.USERS.create(), "Benutzer");

        final TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.add(courtManagement, courtManagementView);
        tabSheet.add(userManagement, userManagementView);

        add(tabSheet);
    }

    @Nonnull
    private Tab createTab(@Nonnull final Icon icon,
                          @Nonnull final String label) {
        final Tab tab = new Tab(icon, new Span(label));
//        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        return tab;
    }
}
