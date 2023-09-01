package ch.fortylove.util;

import ch.fortylove.service.util.DatabaseResult;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
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

    public static void informationNotification(@Nonnull final String text) {
        getDefaultNotification(text).ifPresent(Notification::open);
    }

    public static void persistentInformationNotification(@Nonnull final String message) {
        final Notification notification = new Notification();
        notification.setDuration(0);
        notification.setPosition((POSITION));
        notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

        final Div content = new Div(new Text(message));
        content.addClickListener(event -> notification.close());

        notification.add(content);
        notification.open();
    }

    public static void databaseNotification(@Nonnull final DatabaseResult<?> databaseResult,
                                            @Nonnull final String successMessage) {
        if (databaseResult.isSuccessful()) {
            informationNotification(successMessage);
        } else {
            errorNotification(databaseResult.getMessage());
        }
    }

    @Nonnull
    private static Optional<Notification> getDefaultNotification(@Nonnull final String text) {
        return text.isBlank() ?
                Optional.empty() :
                Optional.of(new Notification(text, DURATION, POSITION));
    }
}
