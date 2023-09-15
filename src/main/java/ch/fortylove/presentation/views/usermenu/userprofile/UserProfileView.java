package ch.fortylove.presentation.views.usermenu.userprofile;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.components.containers.ElevatedPanel;
import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.security.AuthenticationService;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;

import java.util.Optional;

@Route(value = UserProfileView.ROUTE, layout = MainLayout.class)
@PageTitle(UserProfileView.PAGE_TITLE)
@PermitAll
public class UserProfileView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "userprofile";
    @Nonnull public static final String PAGE_TITLE = "Benutzerprofil";

    @Nonnull private final AuthenticationService authenticationService;

    public UserProfileView(@Nonnull final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        initLayout();
    }

    private void initLayout() {
        ElevatedPanel userProfilePanel = new ElevatedPanel();
        Span membershipStatusLabel = new Span("Mitgliedschaftsstatus: ");
        Span membershipStatusValue = new Span(getMembershipStatus());

        membershipStatusValue.getElement().getThemeList().add("badge pill");
        userProfilePanel.add(membershipStatusLabel, membershipStatusValue);
        add(userProfilePanel);
    }

    private String getMembershipStatus() {
        final Optional<User> authenticatedUser = authenticationService.getAuthenticatedUser();
        return authenticatedUser.map(user -> user.getUserStatus().getName()).orElse("Unbekannt");

    }
}