package ch.fortylove.presentation.views.booking.articlecomponent;

import ch.fortylove.persistence.entity.Article;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.components.dialog.DeleteConfirmationDialog;
import ch.fortylove.presentation.views.booking.articlecomponent.articledialog.ArticleDialog;
import ch.fortylove.presentation.views.booking.articlecomponent.articledialog.ArticleDialogEvent;
import ch.fortylove.presentation.views.booking.articlecomponent.events.DeleteArticleEvent;
import ch.fortylove.presentation.views.booking.articlecomponent.events.EditArticleEvent;
import ch.fortylove.presentation.views.booking.articlecomponent.events.NewArticleEvent;
import ch.fortylove.presentation.views.management.Refreshable;
import ch.fortylove.security.AuthenticationService;
import ch.fortylove.service.ArticleService;
import ch.fortylove.service.RoleService;
import ch.fortylove.service.util.DatabaseResult;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringComponent
@UIScope
public class ArticleGrid extends Grid<Article> implements Refreshable {

    @Nonnull private final ArticleService articleService;
    @Nonnull private final RoleService roleService;
    @Nonnull private final AuthenticationService authenticationService;
    @Nonnull private final NotificationUtil notificationUtil;
    @Nonnull private final ArticleDialog articleDialog;

    private Article newArticleItem;

    @Autowired
    public ArticleGrid(@Nonnull final ArticleService articleService,
                       @Nonnull final RoleService roleService,
                       @Nonnull final AuthenticationService authenticationService,
                       @Nonnull final NotificationUtil notificationUtil,
                       @Nonnull final ArticleDialog articleDialog) {
        super(Article.class, false);
        this.articleService = articleService;
        this.roleService = roleService;
        this.authenticationService = authenticationService;
        this.notificationUtil = notificationUtil;
        this.articleDialog = articleDialog;

        addClassName("article-component");
        setSelectionMode(SelectionMode.NONE);
        setAllRowsVisible(true);
        addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);

        constructUI();
        initArticleDialog();
    }

    private void initArticleDialog() {
        articleDialog.addArticleDialogListener(this::articleDialogEvent);
    }

    public void refresh() {
        final List<Article> articles = articleService.findAll();

        final Optional<User> authenticatedUser = authenticationService.getAuthenticatedUser();
        authenticatedUser.ifPresent(user -> {
            final boolean userHasManagementRole = roleService.hasManagementRole(user);
            if (userHasManagementRole) {
                // create article to identify last article in list
                newArticleItem = getNewArticleItem();
                articles.add(newArticleItem);
            } else {
                if (articles.isEmpty()) {
                    setVisible(false);
                    return;
                }
            }
            setItems(articles);
        });
    }

    @Nonnull
    private Article getNewArticleItem() {
        return new Article("", "", null);
    }

    private void constructUI() {
        final Optional<User> authenticatedUser = authenticationService.getAuthenticatedUser();
        authenticatedUser.ifPresent(user -> {
            final boolean userHasManagementRole = roleService.hasManagementRole(user);
            addComponentColumn(article -> {
                if (article.equals(newArticleItem)) {
                    return getNewArticleGridItem();
                } else {
                    return getArticleGridItem(userHasManagementRole, article);
                }
            });
        });
    }

    @Nonnull
    private NewArticleGridItem getNewArticleGridItem() {
        final NewArticleGridItem newArticleGridItem = new NewArticleGridItem();
        newArticleGridItem.addNewArticleListener(this::newArticleEvent);
        return newArticleGridItem;
    }

    @Nonnull
    private ArticleGridItem getArticleGridItem(final boolean userHasManagementRole,
                                               @Nonnull final Article article) {
        final ArticleGridItem articleGridItem = new ArticleGridItem(article, userHasManagementRole);
        if (userHasManagementRole) {
            articleGridItem.addEditArticleListener(this::editArticleEvent);
            articleGridItem.addDeleteArticleListener(this::deleteArticleEvent);
        }
        return articleGridItem;
    }

    private void newArticleEvent(@Nonnull final NewArticleEvent newArticleEvent) {
        articleDialog.openNew();
    }

    private void editArticleEvent(@Nonnull final EditArticleEvent editArticleEvent) {
        final Article article = editArticleEvent.getArticle();
        articleDialog.openEdit(article);
    }

    private void deleteArticleEvent(@Nonnull final DeleteArticleEvent deleteArticleEvent) {
        final Article article = deleteArticleEvent.getArticle();

        new DeleteConfirmationDialog(article.getIdentifier(), "Artikel wirklich löschen?", () -> {
            final DatabaseResult<UUID> deleteResult = articleService.delete(article.getId());
            notificationUtil.databaseNotification(deleteResult);
            refresh();
        }).open();
    }

    private void articleDialogEvent(@Nonnull final ArticleDialogEvent articleDialogEvent) {
        final Article article = articleDialogEvent.getArticle();

        switch (articleDialogEvent.getType()) {
            case NEW -> notificationUtil.databaseNotification(articleService.create(article));
            case EDIT -> notificationUtil.databaseNotification(articleService.update(article));
        }
        refresh();
    }
}
