package ch.fortylove.presentation.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BadgeFactory {

    @Nonnull
    public Component newPrimaryEmptyBadge(@Nonnull final String text) {
        return newPrimaryEmptyBadge(text, null);
    }

    @Nonnull
    public Component newPrimaryEmptyBadge(@Nonnull final VaadinIcon vaadinIcon) {
        return newPrimaryEmptyBadge(null, vaadinIcon);
    }

    @Nonnull
    public Component newPrimaryEmptyBadge(@Nonnull final String text,
                                          @Nonnull final VaadinIcon vaadinIcon) {
        return newBadge(text, vaadinIcon, ColorType.PRIMARY, Size.SMALL, Form.SQUARE, Fill.EMPTY);
    }

    @Nonnull
    private Component newBadge(@Nullable final String text,
                               @Nullable final VaadinIcon vaadinIcon,
                               @Nonnull final ColorType colorType,
                               @Nonnull final Size size,
                               @Nonnull final Form form,
                               @Nonnull final Fill fill) {
        final String css = "badge" + colorType.css + size.css + form.css + fill.css;

        Icon icon = null;
        if (vaadinIcon != null) {
            icon = vaadinIcon.create();
            icon.getStyle().set("padding", "var(--lumo-space-xs");
            if (text == null) {
                icon.getElement().getThemeList().add(css);
                return icon;
            }
        }

        if (text != null) {
            final Span badge = icon != null ?
                    new Span(icon, new Span(text)) :
                    new Span(text);
            badge.getElement().getThemeList().add(css);
            return badge;
        }

        throw new IllegalArgumentException("text and icon can't both be null");
    }

    enum ColorType {
        PRIMARY(""),
        SUCCESS(" success"),
        ERROR(" error"),
        CONTRAST(" contrast");

        @Nonnull private final String css;

        ColorType(@Nonnull final String css) {
            this.css = css;
        }
    }

    enum Size {
        NORMAL(""),
        SMALL(" small");

        @Nonnull private final String css;

        Size(@Nonnull final String css) {
            this.css = css;
        }
    }

    enum Form {
        SQUARE(""),
        PILL(" pill");

        @Nonnull private final String css;

        Form(@Nonnull final String css) {
            this.css = css;
        }
    }

    enum Fill {
        EMPTY(""),
        FILL(" primary");

        @Nonnull private final String css;

        Fill(@Nonnull final String css) {
            this.css = css;
        }
    }
}
