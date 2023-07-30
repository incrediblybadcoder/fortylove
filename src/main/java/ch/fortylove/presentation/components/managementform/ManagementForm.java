package ch.fortylove.presentation.components.managementform;

import ch.fortylove.presentation.components.DeleteConfirmationDialog;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

@SpringComponent
public abstract class ManagementForm<T> extends FormLayout implements FormObservable<T> {

    @Nonnull private final Binder<T> binder;
    @Nonnull private final List<FormObserver<T>> formObservers;

    private Button save;
    private Button update;
    private Button delete;
    private Button close;
    private VerticalLayout buttonContainer;

    @Nullable private T currentItem;

    public ManagementForm() {
        formObservers = new ArrayList<>();
        addClassNames(LumoUtility.Border.ALL, LumoUtility.BorderColor.CONTRAST_20);

        instantiateFields();
        binder = getBinder();

        constructUI();
        closeForm();
    }

    protected abstract void instantiateFields();

    protected abstract Binder<T> getBinder();

    protected abstract VerticalLayout getContent();

    protected abstract T getNewItem();

    protected abstract String getItemIdentifier(@Nonnull final T item);

    protected abstract String getItemName();

    private void constructUI() {
        initializeBinder();

        add(getContent(), getButtons());
    }

    private void initializeBinder() {
        binder.addValueChangeListener(event -> updateButtonState(true));
    }

    private void updateButtonState(final boolean checkChanges) {
        final boolean isValid = binder.isValid();
        final boolean hasChanges = !checkChanges || binder.hasChanges();
        final boolean isEnabled = isValid && hasChanges;
        save.setEnabled(isEnabled);
        update.setEnabled(isEnabled);
        delete.setEnabled(true);
        close.setEnabled(true);
    }

    @Nonnull
    private VerticalLayout getButtons() {
        save = new Button("Speichern");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setWidthFull();
        save.addClickListener(click -> saveClick());

        update = new Button("Speichern");
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.setWidthFull();
        update.addClickListener(click -> updateClick());

        delete = new Button("Löschen");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.setWidthFull();
        delete.addClickListener(click -> deleteClick());

        close = new Button("Abbrechen");
        close.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        close.setWidthFull();
        close.addClickShortcut(Key.ESCAPE);
        close.addClickListener(click -> closeClick());

        buttonContainer = new VerticalLayout(save, update, delete, close);
        return buttonContainer;
    }

    private void saveClick() {
        if (binder.writeBeanIfValid(currentItem)) {
            formObservers.forEach(formObserver -> formObserver.saveEvent(currentItem));
            closeForm();
        }
    }

    private void updateClick() {
        if (binder.writeBeanIfValid(currentItem)) {
            formObservers.forEach(formObserver -> formObserver.updateEvent(currentItem));
            closeForm();
        }
    }

    private void deleteClick() {
        new DeleteConfirmationDialog(
                getItemIdentifier(currentItem),
                getItemName() + " wirklich löschen?",
                () -> {
                    formObservers.forEach(formObserver -> formObserver.deleteEvent(currentItem));
                    closeForm();
                }
        ).open();
    }

    private void closeClick() {
        closeForm();
    }

    public void openCreate() {
        beforeOpen();
        currentItem = getNewItem();
        binder.readBean(currentItem);
        addButtons(save, close);
        updateButtonState(false);
        setVisible(true);
    }

    public void openUpdate(@Nonnull final T item) {
        beforeOpen();
        currentItem = item;
        binder.readBean(currentItem);
        addButtons(update, delete, close);
        updateButtonState(true);
        setVisible(true);
    }

    /**
     * Is called before any open procedures are made.
     * Can be used to do additional steps before loading the selected item.
     * For example filling comboboxes or radiogroups with selection items.
     */
    protected void beforeOpen() {
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

    @Override
    public void addFormObserver(@Nonnull final FormObserver<T> listener) {
        formObservers.add(listener);
    }
}
