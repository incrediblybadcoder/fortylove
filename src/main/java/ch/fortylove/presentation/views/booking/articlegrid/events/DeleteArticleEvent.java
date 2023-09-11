package ch.fortylove.presentation.views.booking.articlegrid.events;

import ch.fortylove.persistence.entity.Article;
import ch.fortylove.presentation.views.booking.articlegrid.ArticleGridItem;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public class DeleteArticleEvent extends ComponentEvent<ArticleGridItem> {

    @Nonnull private final Article article;

    public DeleteArticleEvent(@Nonnull final ArticleGridItem source,
                              @Nonnull final Article article) {
        super(source, false);
        this.article = article;
    }

    @Nonnull
    public Article getArticle() {
        return article;
    }
}

