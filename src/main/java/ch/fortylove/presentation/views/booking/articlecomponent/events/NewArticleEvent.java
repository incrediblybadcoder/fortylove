package ch.fortylove.presentation.views.booking.articlecomponent.events;

import ch.fortylove.presentation.views.booking.articlecomponent.NewArticleGridItem;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public class NewArticleEvent extends ComponentEvent<NewArticleGridItem> {

    public NewArticleEvent(@Nonnull final NewArticleGridItem source) {
        super(source, false);
    }
}

