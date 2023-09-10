package ch.fortylove.presentation.views.booking.articlecomponent;

import ch.fortylove.persistence.entity.Article;
import ch.fortylove.presentation.views.management.Refreshable;
import ch.fortylove.service.ArticleService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
@UIScope
public class ArticleComponent extends VerticalLayout implements Refreshable {

    @Nonnull private final ArticleService articleService;
    @Nonnull private final ArticleGrid articleGrid;

    @Autowired
    public ArticleComponent(@Nonnull final ArticleService articleService,
                            @Nonnull final ArticleGrid articleGrid) {
        this.articleService = articleService;
        this.articleGrid = articleGrid;

        addClassName("article-component");
        constructUI();
    }

    private void constructUI() {
        add(getArticleGrid());
    }

    @Nonnull
    private ArticleGrid getArticleGrid() {
        return articleGrid;
    }

    @Override
    public void refresh() {
        final List<Article> articles = articleService.findAll();

        if (!articles.isEmpty()) {
            articleGrid.refresh();
        }
    }
}
