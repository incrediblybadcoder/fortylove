package ch.fortylove.presentation.views.management.courtmanagement;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtIcon;
import ch.fortylove.persistence.entity.CourtType;
import ch.fortylove.presentation.components.InputFieldFactory;
import ch.fortylove.presentation.components.managementform.ManagementForm;
import com.vaadin.flow.component.Focusable;
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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CourtForm extends ManagementForm<Court> {

    private IntegerField numberField;
    private Select<CourtIcon> iconSelection;
    private Select<CourtType> courtTypeSelection;
    private TextField nameField;

    @Override
    protected void instantiateFields() {
        numberField = InputFieldFactory.createBasicIntegerField("Nummer", true, 1, 99);
        iconSelection = new Select<>();
        courtTypeSelection = new Select<>();
        nameField = InputFieldFactory.createTextField("Name", false);
    }

    @Nonnull
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

    @Nonnull
    @Override
    protected VerticalLayout getContent() {
        return new VerticalLayout(numberField, getIconSelection(), getCourtTypeSelection(), getNameField());
    }

    @Nonnull
    @Override
    protected Court getNewItem() {
        return new Court(CourtType.CLAY, CourtIcon.ORANGE, 1, "");
    }

    @Nonnull
    @Override
    protected String getItemIdentifier(@Nonnull final Court court) {
        return court.getIdentifier();
    }

    @Nonnull
    @Override
    protected String getItemName() {
        return "Platz";
    }

    @Nonnull
    @Override
    protected Focusable<IntegerField> getFocusOnOpen() {
        return numberField;
    }

    @Nonnull
    private Select<CourtIcon> getIconSelection() {
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
        courtTypeSelection.setWidthFull();
        courtTypeSelection.setLabel("Belag");
        courtTypeSelection.setItems(CourtType.values());
        courtTypeSelection.setItemLabelGenerator(CourtType::getMaterial);

        return courtTypeSelection;
    }

    @Nonnull
    private TextField getNameField() {
        nameField.setWidthFull();
        return nameField;
    }
}

