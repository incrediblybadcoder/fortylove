package ch.fortylove.presentation.views.courtmanagement;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtIcon;
import ch.fortylove.presentation.views.courtmanagement.events.CloseEvent;
import ch.fortylove.presentation.views.courtmanagement.events.DeleteEvent;
import ch.fortylove.presentation.views.courtmanagement.events.SaveEvent;
import ch.fortylove.presentation.views.courtmanagement.events.UpdateEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

@SpringComponent
public class CourtForm extends FormLayout {

    private IntegerField number;
    private TextField name;
    private RadioButtonGroup<CourtIcon> icon;

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
        initializeButtons();
        initializeButtonsContainer();
        initializeCheckboxGroup();
        add(createInputFieldsLayout(), icon, buttonContainer);
        initializeBinder();
    }

    private void initializeBinder() {
        binder = new Binder<>(Court.class);

        binder.forField(number)
                .withValidator(numberInput -> numberInput > 0, "Nummer muss grösser 0 sein")
                .bind(Court::getNumber, Court::setNumber);

        binder.forField(name).bind(Court::getName, Court::setName);

        binder.forField(icon).bind(Court::getCourtIcon, Court::setCourtIcon);

        binder.addValueChangeListener(event -> updateButtonState());
        binder.bindInstanceFields(this);
    }

    private void initializeButtons() {
        save = new Button("Speichern");
        update = new Button("Aktualisieren");
        delete = new Button("Löschen");
        close = new Button("Abbrechen");

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        update.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        update.addClickListener(click -> validateAndUpdate());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, currentCourt)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));
    }

    private void initializeButtonsContainer() {
        buttonContainer = new VerticalLayout();
        buttonContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    }

    private void initializeCheckboxGroup() {
        icon = new RadioButtonGroup<>();
        icon.setLabel("Icon");
        icon.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        icon.setItems(CourtIcon.values());
        icon.setRenderer(new ComponentRenderer<>(courtIcon -> {
            final Image icon = new Image(courtIcon.getResource(), courtIcon.getCode());
            icon.setHeight("1em");
            return new Div(icon);
        }));
    }

    @Nonnull
    private VerticalLayout createInputFieldsLayout() {
        number = new IntegerField("Nummer");
        number.setValueChangeMode(ValueChangeMode.EAGER);
        number.setRequired(true);
        number.setRequiredIndicatorVisible(true);
        number.setMin(0);
        number.setStep(1);
        number.setStepButtonsVisible(true);

        name = new TextField("Name");
        name.setValueChangeMode(ValueChangeMode.EAGER);

        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(number, name);

        return verticalLayout;
    }

    private void updateButtonState() {
        final boolean ok = binder.isValid();
        save.setEnabled(ok);
        update.setEnabled(ok);
        delete.setEnabled(true);
        close.setEnabled(true);
    }

    public void openNewCourt() {
        addButtons(save, close);
    }

    public void openEditCourt() {
        addButtons(update, delete, close);
    }

    private void addButtons(@Nonnull final Button... buttons) {
        buttonContainer.removeAll();
        buttonContainer.add(buttons);
    }

    public void setCourt(@Nullable final Court user) {
        this.currentCourt = user;
        binder.readBean(user);
    }

    private void validateAndSave() {
        if (binder.writeBeanIfValid(currentCourt)) {
            fireEvent(new SaveEvent(this, currentCourt));
        }
    }

    private void validateAndUpdate() {
        if (binder.writeBeanIfValid(currentCourt)) {
            fireEvent(new UpdateEvent(this, currentCourt));
        }
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

