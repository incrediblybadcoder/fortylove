package ch.fortylove.presentation.views.feedback;

import ch.fortylove.presentation.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;

@Route(value = FeedbackView.ROUTE, layout = MainLayout.class)
@PageTitle(FeedbackView.PAGE_TITLE)
@PermitAll
public class FeedbackView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "feedback";
    @Nonnull public static final String PAGE_TITLE = "Feedback";

    public FeedbackView(){
    }
}