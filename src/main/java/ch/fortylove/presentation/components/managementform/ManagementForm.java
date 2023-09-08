package ch.fortylove.presentation.components.managementform;

import ch.fortylove.presentation.components.BadgeFactory;
import ch.fortylove.presentation.components.ButtonFactory;
import ch.fortylove.presentation.components.dialog.DeleteConfirmationDialog;
import ch.fortylove.presentation.components.dialog.Dialog;
import ch.fortylove.presentation.components.managementform.events.ManagementFormDeleteEvent;
import ch.fortylove.presentation.components.managementform.events.ManagementFormModifyEvent;
import ch.fortylove.presentation.components.managementform.events.ManagementFormSaveEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public abstract class ManagementForm<T> extends FormLayout {

    @Autowired private BadgeFactory badgeFactory;

    private Binder<T> binder;

    private H3 title;

    private Button save;
    private Button update;
    private Button delete;
    private Button close;
    private VerticalLayout buttonContainer;

    protected T currentItem;

    public ManagementForm() {

        addClassNames(LumoUtility.Border.ALL, LumoUtility.BorderColor.CONTRAST_20);

        constructUI();
        closeForm();
    }

    protected abstract void instantiateFields();

    @Nonnull protected abstract Binder<T> getBinder();

    @Nonnull protected abstract VerticalLayout getContent();

    @Nonnull protected abstract T getNewItem();

    @Nonnull protected abstract String getItemIdentifier(@Nonnull final T item);

    @Nonnull protected abstract String getItemName();

    @Nonnull protected abstract Focusable<? extends Component> getFocusOnOpen();

    private void constructUI() {
        initializeBinder();
        add(getTitle(), getContent(), getButtons());
    }

    private void initializeBinder() {
        instantiateFields();
        binder = getBinder();
        binder.addValueChangeListener(event -> updateButtonState(true));
    }

    private void updateButtonState(final boolean checkChanges) {
        final boolean isValid = binder.isValid();
        final boolean hasChanges = !checkChanges || binder.hasChanges();
        final boolean isEnabled = isValid && hasChanges;
        save.setEnabled(isEnabled);
        update.setEnabled(isEnabled);
        delete.setEnabled(isDeleteEnabled());
    }

    protected boolean isDeleteEnabled() {
        return true;
    }

    @Nonnull
    private Component getTitle() {
        title = new H3();
        return new VerticalLayout(title);
    }

    @Nonnull
    private VerticalLayout getButtons() {

        save = ButtonFactory.createPrimaryButton("Speichern", this::saveClick);

        update = ButtonFactory.createPrimaryButton("Updaten", this::updateClick);

        delete = ButtonFactory.createDangerButton("Löschen", this::deleteClick);

        close = ButtonFactory.createNeutralButton("Abbrechen", this::closeClick);
        close.addClickShortcut(Key.ESCAPE);

        buttonContainer = new VerticalLayout();
        return buttonContainer;
    }

    private void saveClick() {
        if (binder.writeBeanIfValid(currentItem)) {
            fireEvent(new ManagementFormSaveEvent<>(this, currentItem));
            closeForm();
        }
    }

    private void updateClick() {
        if (binder.writeBeanIfValid(currentItem)) {
            fireEvent(new ManagementFormModifyEvent<>(this, currentItem));
            closeForm();
        }
    }

    private void deleteClick() {
        getDeleteConfirmationDialog().open();
    }

    @Nonnull
    protected Dialog getDeleteConfirmationDialog() {
        return new DeleteConfirmationDialog(
                getItemIdentifier(currentItem),
                getItemName() + " wirklich löschen?",
                () -> {
                    fireEvent(new ManagementFormDeleteEvent<>(this, currentItem));
                    closeForm();
                }
        );
    }

    private void closeClick() {
        closeForm();
    }

    public void openCreate() {
        final String title = getItemName() + " erstellen";
        final Button[] buttons = {save, close};
        open(title, getNewItem(), false, buttons);
    }

    public void openModify(@Nonnull final T item) {
        final String title = getItemName() + " bearbeiten";
        final Button[] buttons = {update, delete, close};
        open(title, item, true, buttons);
    }

    private void open(@Nonnull final String title,
                      @Nonnull final T item,
                      final boolean checkChanges,
                      @Nonnull final Button... buttons) {

        beforeOpen();

        this.title.setText(title);
        currentItem = item;
        binder.readBean(currentItem);
        addButtons(buttons);
        updateButtonState(checkChanges);
        getFocusOnOpen().focus();

        afterOpen();

        setVisible(true);
    }

    /**
     * Is called before any open procedures are made.
     * Can be used to do additional steps before loading the selected item.
     * For example filling comboboxes or radiogroups with selection items.
     */
    protected void beforeOpen() {
    }

    /**
     * Is called after any open procedures are made.
     * Can be used to do additional steps after loading the selected item.
     * For example filling comboboxes or radiogroups with selection items.
     */
    protected void afterOpen() {
    }

    public void closeForm() {
        currentItem = null;
        binder.readBean(null);
        setVisible(false);
    }

    private void addButtons(@Nonnull final Button... buttons) {
        buttonContainer.removeAll();
        buttonContainer.add(buttons);
    }

    public void addSaveEventListener(@Nonnull final ComponentEventListener<ManagementFormSaveEvent<T>> listener) {
        //noinspection unchecked,rawtypes
        addListener(ManagementFormSaveEvent.class, (ComponentEventListener) listener);
    }

    public void addModifyEventListener(@Nonnull final ComponentEventListener<ManagementFormModifyEvent<T>> listener) {
        //noinspection unchecked,rawtypes
        addListener(ManagementFormModifyEvent.class, (ComponentEventListener) listener);
    }

    public void addDeleteEventListener(@Nonnull final ComponentEventListener<ManagementFormDeleteEvent<T>> listener) {
        //noinspection unchecked,rawtypes
        addListener(ManagementFormDeleteEvent.class, (ComponentEventListener) listener);
    }

    @Nonnull
    protected BadgeFactory getBadgeFactory() {
        return badgeFactory;
    }
}
