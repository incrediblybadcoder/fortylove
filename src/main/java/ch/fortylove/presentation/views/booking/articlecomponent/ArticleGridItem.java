package ch.fortylove.presentation.views.booking.articlecomponent;

import ch.fortylove.persistence.entity.Article;
import ch.fortylove.presentation.views.booking.articlecomponent.events.DeleteArticleEvent;
import ch.fortylove.presentation.views.booking.articlecomponent.events.EditArticleEvent;
import ch.fortylove.util.FormatUtil;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;

public class ArticleGridItem extends HorizontalLayout {

    public ArticleGridItem(@Nonnull final Article article,
                           final boolean hasActions) {
        addClassName("item");
        setSpacing(false);
        getThemeList().add("spacing-s");

        constructUI(article, hasActions);
    }

    private void constructUI(@Nonnull final Article article,
                             final boolean hasActions) {
        final VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        final HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        final Span title = new Span(article.getTitle());
        title.addClassName("title");
        final Span date = new Span(FormatUtil.format(article.getDateTime()));
        date.addClassName("date");
        header.add(title, date);

        final Span text = new Span(article.getText());
        text.addClassName("text");

        description.add(header, text);

        if (hasActions) {
            final HorizontalLayout actions = new HorizontalLayout();
            actions.addClassName("actions");
            actions.setSpacing(false);
            actions.getThemeList().add("spacing-s");

            final Icon editIcon = VaadinIcon.EDIT.create();
            editIcon.addClassName("icon");
            editIcon.addClickListener(event -> fireEvent(new EditArticleEvent(this, article)));
            final Icon deleteIcon = VaadinIcon.TRASH.create();
            deleteIcon.addClassName("icon");
            deleteIcon.addClickListener(event -> fireEvent(new DeleteArticleEvent(this, article)));

            actions.add(editIcon, deleteIcon);
            description.add(actions);
        }

        add(description);
    }

    public void addDeleteArticleListener(@Nonnull final ComponentEventListener<DeleteArticleEvent> listener) {
        addListener(DeleteArticleEvent.class, listener);
    }

    public void addEditArticleListener(@Nonnull final ComponentEventListener<EditArticleEvent> listener) {
        addListener(EditArticleEvent.class, listener);
    }
}
