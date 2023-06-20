package ch.fortylove.persistence.setupdata;

import ch.fortylove.persistence.setupdata.settings.BookingSettingsSetupData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

@Component
@Profile({"h2", "develop", "local", "production"})
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Nonnull private final BookingSettingsSetupData bookingSettingsSetupData;

    private boolean alreadySetup = false;

    @Autowired
    public SetupDataLoader(@Nonnull final BookingSettingsSetupData bookingSettingsSetupData) {
        this.bookingSettingsSetupData = bookingSettingsSetupData;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@Nonnull final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        bookingSettingsSetupData.createBookingSettings();

        alreadySetup = true;
    }
}