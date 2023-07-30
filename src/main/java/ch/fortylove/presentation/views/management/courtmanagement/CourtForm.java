package ch.fortylove.presentation.views.management.courtmanagement;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtIcon;
import ch.fortylove.persistence.entity.CourtType;
import ch.fortylove.presentation.components.managementform.ManagementForm;
import com.vaadin.flow.component.Component;
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
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.Nonnull;

@SpringComponent
@UIScope
public class CourtForm extends ManagementForm<Court> {

    private IntegerField numberField;
    private Select<CourtIcon> iconSelection;
    private Select<CourtType> courtTypeSelection;
    private TextField nameField;

    @Override
    protected void instantiateFields() {
        numberField = new IntegerField();
        iconSelection = new Select<>();
        courtTypeSelection = new Select<>();
        nameField = new TextField();
    }

    @Override
    protected Binder<Court> getBinder() {
        final Binder<Court> binder = new Binder<>(Court.class);

        binder.forField(numberField).bind(Court::getNumber, Court::setNumber);
        binder.forField(iconSelection).bind(Court::getCourtIcon, Court::setCourtIcon);
        binder.forField(courtTypeSelection).bind(Court::getCourtType, Court::setCourtType);
        binder.forField(nameField).bind(Court::getName, Court::setName);

        binder.bindInstanceFields(this);
        return binder;
    }

    @Override
    protected VerticalLayout getContent() {
        return new VerticalLayout(getNumberField(), getIconSelection(), getCourtTypeSelection(), getNameField());
    }

    @Override
    protected Court getNewItem() {
        return new Court(CourtType.CLAY, CourtIcon.ORANGE, 1, "");
    }

    @Override
    protected String getItemIdentifier(@Nonnull final Court court) {
        return court.getIdentifier();
    }

    @Override
    protected String getItemName() {
        return "Platz";
    }

    @Nonnull
    private Component getNumberField() {
        numberField.setLabel("Nummer");
        numberField.setWidthFull();
        numberField.setMin(1);
        numberField.setStep(1);
        numberField.setStepButtonsVisible(true);
        return numberField;
    }

    @Nonnull
    private Component getIconSelection() {
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
    private Component getCourtTypeSelection() {
        courtTypeSelection.setWidthFull();
        courtTypeSelection.setLabel("Typ");
        courtTypeSelection.setItems(CourtType.values());
        courtTypeSelection.setItemLabelGenerator(CourtType::getMaterial);

        return courtTypeSelection;
    }

    @Nonnull
    private Component getNameField() {
        nameField.setWidthFull();
        nameField.setLabel("Name");
        nameField.setValueChangeMode(ValueChangeMode.EAGER);
        return nameField;
    }
}

