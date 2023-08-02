package ch.fortylove.presentation.components.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import jakarta.annotation.Nonnull;

public class DeleteConfirmationDialog extends CancelableDialog {

    public DeleteConfirmationDialog(@Nonnull final String header,
                                    @Nonnull final String text,
                                    @Nonnull final Runnable onConfirmAction) {
        constructUI(header, text, onConfirmAction);
    }

    private void constructUI(@Nonnull final String header,
                             @Nonnull final String text,
                             @Nonnull final Runnable onConfirmAction) {
        setHeaderTitle(header);

        final Span textSpan = new Span(text);

        final Button deleteButton = new Button("Löschen");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener(event -> {
            onConfirmAction.run();
            close();
        });

        add(textSpan);
        getFooter().add(deleteButton);
    }
}
