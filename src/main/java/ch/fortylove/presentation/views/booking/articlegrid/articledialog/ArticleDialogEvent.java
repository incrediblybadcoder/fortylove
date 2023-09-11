package ch.fortylove.presentation.views.booking.articlegrid.articledialog;

import ch.fortylove.persistence.entity.Article;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public class ArticleDialogEvent extends ComponentEvent<ArticleDialog> {

    @Nonnull private final Article article;
    @Nonnull private final Type type;

    public enum Type {
        NEW,
        EDIT
    }

    protected ArticleDialogEvent(@Nonnull final ArticleDialog source,
                                 @Nonnull final Article article,
                                 @Nonnull final Type type) {
        super(source, false);
        this.article = article;
        this.type = type;
    }

    @Nonnull
    public static ArticleDialogEvent newArticle(@Nonnull final ArticleDialog source,
                                                @Nonnull final Article article) {
        return new ArticleDialogEvent(source, article, Type.NEW);
    }

    @Nonnull
    public static ArticleDialogEvent editArticle(@Nonnull final ArticleDialog source,
                                                 @Nonnull final Article article) {
        return new ArticleDialogEvent(source, article, Type.EDIT);
    }

    @Nonnull
    public Article getArticle() {
        return article;
    }

    @Nonnull
    public Type getType() {
        return type;
    }
}

