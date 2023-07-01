package ch.fortylove.persistence.setupdata;

import ch.fortylove.persistence.setupdata.settings.BookingSettingsSetupData;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

@Component
@Profile({"h2", "develop", "local", "production"})
public class SetupDataLoader implements InitializingBean {

    @Nonnull private final BookingSettingsSetupData bookingSettingsSetupData;

    private boolean alreadySetup = false;

    @Autowired
    public SetupDataLoader(@Nonnull final BookingSettingsSetupData bookingSettingsSetupData) {
        this.bookingSettingsSetupData = bookingSettingsSetupData;
    }

    @Override
    @Transactional
    public void afterPropertiesSet() throws Exception {
        if (alreadySetup) {
            return;
        }

        bookingSettingsSetupData.createBookingSettings();

        alreadySetup = true;
    }
}