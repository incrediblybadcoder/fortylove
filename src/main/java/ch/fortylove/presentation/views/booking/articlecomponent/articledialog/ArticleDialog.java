package ch.fortylove.presentation.views.booking.articlecomponent.articledialog;

import ch.fortylove.persistence.entity.Article;
import ch.fortylove.presentation.components.dialog.CancelableDialog;
import ch.fortylove.presentation.fieldvalidators.NotBlankValidator;
import ch.fortylove.util.DateTimeUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ArticleDialog extends CancelableDialog {

    @Nonnull private final DateTimeUtil dateTimeUtil;

    @Nonnull private final Binder<Article> binder = new Binder<>();
    @Nonnull private final Button newButton = new Button("Speichern");
    @Nonnull private final Button editButton = new Button("Speichern");
    @Nonnull private final TextField titleField = new TextField("Titel");
    @Nonnull private final TextArea textArea = new TextArea("Text");
    @Nonnull private final HorizontalLayout buttonContainer = new HorizontalLayout();

    private Article currentArticle;

    @Autowired
    public ArticleDialog(@Nonnull final DateTimeUtil dateTimeUtil) {
        this.dateTimeUtil = dateTimeUtil;

        constructUI();
        initBinder();
    }

    private void initBinder() {
        binder.forField(titleField).withValidator(new NotBlankValidator("Titel")).bind(Article::getTitle, Article::setTitle);
        binder.forField(textArea).withValidator(new NotBlankValidator("Text")).bind(Article::getText, Article::setText);
        binder.bindInstanceFields(this);
        binder.addValueChangeListener(event -> updateButtonState(true));
    }

    private void constructUI() {
        setWidth("350px");

        final VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setSpacing(false);
        dialogLayout.setPadding(false);

        titleField.setWidthFull();
        titleField.setRequired(true);
        titleField.setRequiredIndicatorVisible(true);
        titleField.setValueChangeMode(ValueChangeMode.EAGER);

        textArea.setWidthFull();
        textArea.setRequired(true);
        textArea.setRequiredIndicatorVisible(true);
        textArea.setValueChangeMode(ValueChangeMode.EAGER);

        newButton.addClickListener(newButtonClickListener());
        newButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editButton.addClickListener(editButtonClickListener());
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        getFooter().add(buttonContainer);

        dialogLayout.add(titleField, textArea);
        add(dialogLayout);
    }

    private void updateButtonState(final boolean checkChanges) {
        final boolean isValid = binder.isValid();
        final boolean hasChanges = !checkChanges || binder.hasChanges();
        final boolean isEnabled = isValid && hasChanges;
        newButton.setEnabled(isEnabled);
    }

    public void openNew() {
        setHeaderTitle("Artikel erstellen");
        currentArticle = new Article("", "", dateTimeUtil.todayNow());
        binder.readBean(currentArticle);
        updateButtonState(false);
        titleField.focus();
        addButtons(newButton);
        open();
    }

    public void openEdit(@Nonnull final Article article) {
        setHeaderTitle("Artikel bearbeiten");
        currentArticle = article;
        binder.readBean(article);
        updateButtonState(true);
        titleField.focus();
        addButtons(editButton);
        open();
    }

    private void addButtons(@Nonnull final Button... buttons) {
        buttonContainer.removeAll();
        buttonContainer.add(buttons);
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> newButtonClickListener() {
        return event -> {
            if (binder.writeBeanIfValid(currentArticle)) {
                currentArticle.setDateTime(dateTimeUtil.todayNow());
                fireEvent(ArticleDialogEvent.newArticle(this, currentArticle));
                close();
            }
        };
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> editButtonClickListener() {
        return event -> {
            if (binder.writeBeanIfValid(currentArticle)) {
                fireEvent(ArticleDialogEvent.editArticle(this, currentArticle));
                close();
            }
        };
    }

    public void addArticleDialogListener(@Nonnull final ComponentEventListener<ArticleDialogEvent> listener) {
        addListener(ArticleDialogEvent.class, listener);
    }
}