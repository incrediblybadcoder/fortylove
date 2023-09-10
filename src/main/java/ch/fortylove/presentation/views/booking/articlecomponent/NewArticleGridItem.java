package ch.fortylove.presentation.views.booking.articlecomponent;

import ch.fortylove.presentation.views.booking.articlecomponent.events.NewArticleEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import jakarta.annotation.Nonnull;

public class NewArticleGridItem extends HorizontalLayout {

    public NewArticleGridItem() {
        setPadding(true);
        setJustifyContentMode(JustifyContentMode.CENTER);

        constructUI();
    }

    private void constructUI() {
        final Icon newIcon = VaadinIcon.PLUS_CIRCLE.create();
        newIcon.addClassName("new-icon");
        newIcon.addClickListener(event -> fireEvent(new NewArticleEvent(this)));

        add(newIcon);
    }

    public void addNewArticleListener(@Nonnull final ComponentEventListener<NewArticleEvent> listener) {
        addListener(NewArticleEvent.class, listener);
    }
}
