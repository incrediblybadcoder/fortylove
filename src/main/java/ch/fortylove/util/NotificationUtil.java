package ch.fortylove.util;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

import javax.annotation.Nonnull;

public class NotificationUtil {

    private final static int DURATION = 3000;
    @Nonnull private final static Notification.Position POSITION = Notification.Position.TOP_CENTER;

    public static void errorNotification(@Nonnull final String text) {
        final Notification notification = getDefaultNotification(text);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.open();
    }

    public static void infoNotification(@Nonnull final String text) {
        final Notification notification = getDefaultNotification(text);
        notification.open();
    }

    @Nonnull
    private static Notification getDefaultNotification(@Nonnull final String text) {
        return new Notification(text, DURATION, POSITION);
    }
}
