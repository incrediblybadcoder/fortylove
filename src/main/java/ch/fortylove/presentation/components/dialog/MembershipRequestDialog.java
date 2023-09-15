package ch.fortylove.presentation.components.dialog;

import ch.fortylove.presentation.components.ButtonFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;

public class MembershipRequestDialog extends CancelableDialog{
    private final ButtonFactory buttonFactory;
    private final Runnable onSendClick;

    public MembershipRequestDialog(@Nonnull final ButtonFactory buttonFactory, @Nonnull Runnable onSendClick) {
        this.buttonFactory = buttonFactory;
        this.onSendClick = onSendClick;
        initDialog();
    }

    private void initDialog() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setSpacing(true);
        layout.setWidth("100%");

        H2 title = new H2("TCU Mitgliedschaftsanfrage");
        Anchor link = new Anchor("https://www.tcuntervaz.ch/club/reglemente-statuten/", "TCU Statuten");
        link.setTarget("_blank");
        Paragraph description = new Paragraph("Rechte und Pflichten einer Mitgliedschaft im TCU sind in der TCU Statuten festgehalten");

        Button sendButton = buttonFactory.createPrimaryButton("Anfrage senden", event -> {
            onSendClick.run();
            close();
        });
        sendButton.setWidth("auto");

        layout.add(title, description, link, sendButton);
        add(layout);
    }
}
