package ch.fortylove.persistence.entity.factory;

import ch.fortylove.persistence.entity.Theme;
import ch.fortylove.persistence.entity.UserSettings;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;

@SpringComponent
public class UserSettingsFactory {

    @Nonnull
    public UserSettings newUserSettings() {
        return new UserSettings(Theme.LIGHT, true);
    }
}
