package ch.fortylove.presentation.components.managementform;

import ch.fortylove.presentation.components.DeleteConfirmationDialog;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
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
        instantiateFields();
        binder = getBinder();

        constructUI();
        closeForm();
        formObservers = new ArrayList<>();
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
        binder.addValueChangeListener(event -> updateButtonState());
    }

    private void updateButtonState() {
        final boolean isValid = binder.isValid();
        save.setEnabled(isValid);
        update.setEnabled(isValid);
        delete.setEnabled(true);
        close.setEnabled(true);
    }

    @Nonnull
    private VerticalLayout getButtons() {
        save = new Button("Speichern");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setWidthFull();
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(click -> saveClick());

        update = new Button("Speichern");
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.setWidthFull();
        update.addClickShortcut(Key.ENTER);
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
        currentItem = getNewItem();
        binder.readBean(currentItem);
        addButtons(save, close);
        setVisible(true);
    }

    public void openUpdate(@Nonnull final T item) {
        currentItem = item;
        binder.readBean(currentItem);
        addButtons(update, delete, close);
        setVisible(true);
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
