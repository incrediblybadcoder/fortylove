package ch.fortylove.presentation.views.booking.articlegrid.events;

import ch.fortylove.presentation.views.booking.articlegrid.NewArticleGridItem;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public class NewArticleEvent extends ComponentEvent<NewArticleGridItem> {

    public NewArticleEvent(@Nonnull final NewArticleGridItem source) {
        super(source, false);
    }
}

