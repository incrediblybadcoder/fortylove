package ch.fortylove.presentation.views.registration;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.Nonnull;

@Route(value = RegistrationView.ROUTE)
@PageTitle(RegistrationView.PAGE_TITLE)
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "registration";
    @Nonnull public static final String PAGE_TITLE = "Registration";

    public RegistrationView(@Nonnull final RegistrationForm registrationForm) {
        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

        add(registrationForm);
    }
}
