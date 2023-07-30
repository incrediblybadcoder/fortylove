package ch.fortylove.presentation.views.courtmanagement;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtIcon;
import ch.fortylove.persistence.entity.CourtType;
import ch.fortylove.presentation.components.DeleteConfirmationDialog;
import ch.fortylove.presentation.views.courtmanagement.events.CloseEvent;
import ch.fortylove.presentation.views.courtmanagement.events.DeleteEvent;
import ch.fortylove.presentation.views.courtmanagement.events.SaveEvent;
import ch.fortylove.presentation.views.courtmanagement.events.UpdateEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

@SpringComponent
public class CourtForm extends FormLayout {

    private Button save;
    private Button update;
    private Button delete;
    private Button close;
    private VerticalLayout buttonContainer;

    private Binder<Court> binder;
    @Nullable private Court currentCourt;

    public CourtForm() {
        constructUI();
    }

    private void constructUI() {
        add(initializeContent(), initializeButtons());
    }

    @Nonnull
    private VerticalLayout initializeContent() {
        final IntegerField numberField = getNumberField();
        final Select<CourtIcon> iconSelection = getIconSelection();
        final Select<CourtType> courtTypeSelection = getCourtTypeSelection();
        final TextField nameField = getNameField();

        initializeBinder(numberField, iconSelection, courtTypeSelection, nameField);

        return new VerticalLayout(numberField, iconSelection, courtTypeSelection, nameField);
    }

    @Nonnull
    private IntegerField getNumberField() {
        final IntegerField numberField = new IntegerField("Nummer");
        numberField.setWidthFull();
        numberField.setMin(1);
        numberField.setStep(1);
        numberField.setStepButtonsVisible(true);
        return numberField;
    }

    @Nonnull
    private Select<CourtIcon> getIconSelection() {
        final Select<CourtIcon> iconSelection = new Select<>();
        iconSelection.setWidthFull();
        iconSelection.setLabel("Icon");
        iconSelection.setItems(CourtIcon.values());
        iconSelection.setRenderer(new ComponentRenderer<>(courtIcon -> {
            final Image icon = new Image(courtIcon.getResource(), courtIcon.getCode());
            icon.setHeight("1em");
            final Span courtIconName = new Span(courtIcon.getName());

            final HorizontalLayout horizontalLayout = new HorizontalLayout(icon, courtIconName);
            horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
            return horizontalLayout;
        }));

        return iconSelection;
    }

    @Nonnull
    private Select<CourtType> getCourtTypeSelection() {
        final Select<CourtType> courtTypeSelection = new Select<>();
        courtTypeSelection.setWidthFull();
        courtTypeSelection.setLabel("Typ");
        courtTypeSelection.setItems(CourtType.values());
        courtTypeSelection.setItemLabelGenerator(CourtType::getMaterial);

        return courtTypeSelection;
    }

    @Nonnull
    private TextField getNameField() {
        final TextField nameField = new TextField("Name");
        nameField.setWidthFull();
        return nameField;
    }

    private void initializeBinder(@Nonnull final IntegerField numberField,
                                  @Nonnull final Select<CourtIcon> iconSelection,
                                  @Nonnull final Select<CourtType> courtTypeSelection,
                                  @Nonnull final TextField nameField) {
        binder = new Binder<>(Court.class);

        binder.forField(numberField).bind(Court::getNumber, Court::setNumber);

        binder.forField(iconSelection).bind(Court::getCourtIcon, Court::setCourtIcon);

        binder.forField(courtTypeSelection).bind(Court::getCourtType, Court::setCourtType);

        binder.forField(nameField).bind(Court::getName, Court::setName);

        binder.addValueChangeListener(event -> updateButtonState());
        binder.bindInstanceFields(this);
    }

    @Nonnull
    private VerticalLayout initializeButtons() {
        save = new Button("Speichern");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setWidthFull();
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(click -> save());

        update = new Button("Aktualisieren");
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.setWidthFull();
        update.addClickShortcut(Key.ENTER);
        update.addClickListener(click -> update());

        delete = new Button("Löschen");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.setWidthFull();
        delete.addClickListener(click -> delete());

        close = new Button("Abbrechen");
        close.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        close.setWidthFull();
        close.addClickShortcut(Key.ESCAPE);
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        buttonContainer = new VerticalLayout(save, update, delete, close);
        return buttonContainer;
    }

    private void updateButtonState() {
        final boolean isValid = binder.isValid();
        save.setEnabled(isValid);
        update.setEnabled(isValid);
        delete.setEnabled(true);
        close.setEnabled(true);
    }

    public void openNewCourt() {
        addButtons(save, close);
        setVisible(true);
    }

    public void openEditCourt() {
        addButtons(update, delete, close);
        setVisible(true);
    }

    private void addButtons(@Nonnull final Button... buttons) {
        buttonContainer.removeAll();
        buttonContainer.add(buttons);
    }

    public void setCourt(@Nullable final Court user) {
        this.currentCourt = user;
        binder.readBean(user);
    }

    private void save() {
        if (binder.writeBeanIfValid(currentCourt)) {
            fireEvent(new SaveEvent(this, currentCourt));
        }
    }

    private void update() {
        if (binder.writeBeanIfValid(currentCourt)) {
            fireEvent(new UpdateEvent(this, currentCourt));
        }
    }

    private void delete() {
        new DeleteConfirmationDialog(
                currentCourt.getIdentifier(),
                "Platz wirklich Löschen?",
                () -> fireEvent(new DeleteEvent(this, currentCourt))
        ).open();
    }

    public void addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        addListener(DeleteEvent.class, listener);
    }

    public void addSaveListener(ComponentEventListener<SaveEvent> listener) {
        addListener(SaveEvent.class, listener);
    }

    public void addUpdateListener(ComponentEventListener<UpdateEvent> listener) {
        addListener(UpdateEvent.class, listener);
    }

    public void addCloseListener(ComponentEventListener<CloseEvent> listener) {
        addListener(CloseEvent.class, listener);
    }
}

