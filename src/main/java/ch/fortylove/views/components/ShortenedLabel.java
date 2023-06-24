package ch.fortylove.views.components;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.Nonnull;

public class ShortenedLabel extends VerticalLayout {
    private static final int MAX_LENGTH = 10;

    public ShortenedLabel(@Nonnull final String text) {
        setPadding(false);

        final Label label = new Label();
        label.setText(shortenedText(text.trim()));
        add(label);
    }

    @Nonnull
    private String shortenedText(@Nonnull final String text) {
        if (text.length() <= MAX_LENGTH) {
            return text;
        } else {
            return text.substring(0, MAX_LENGTH).trim() + "...";
        }
    }
}