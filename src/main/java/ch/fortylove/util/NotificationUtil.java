package ch.fortylove.util;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import jakarta.annotation.Nonnull;

import java.util.Optional;

public class NotificationUtil {

    private final static int DURATION = 3000;
    @Nonnull private final static Notification.Position POSITION = Notification.Position.TOP_CENTER;

    public static void errorNotification(@Nonnull final String text) {
        getDefaultNotification(text).ifPresent(notification -> {
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        });
    }

    public static void infoNotification(@Nonnull final String text) {
        getDefaultNotification(text).ifPresent(Notification::open);
    }

    @Nonnull
    private static Optional<Notification> getDefaultNotification(@Nonnull final String text) {
        return text.isBlank() ?
                Optional.empty() :
                Optional.of(new Notification(text, DURATION, POSITION));
    }
}
