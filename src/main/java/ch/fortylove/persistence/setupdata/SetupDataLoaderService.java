package ch.fortylove.persistence.setupdata;

import ch.fortylove.devsetupdata.DevSetupData;
import ch.fortylove.persistence.setupdata.settings.BookingSettingsSetupData;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@DevSetupData
public class SetupDataLoaderService {

    @Nonnull private final BookingSettingsSetupData bookingSettingsSetupData;

    private boolean alreadySetup = false;

    @Autowired
    public SetupDataLoaderService(@Nonnull final BookingSettingsSetupData bookingSettingsSetupData) {
        this.bookingSettingsSetupData = bookingSettingsSetupData;
    }

    public void initData() {
        if (alreadySetup) {
            return;
        }

        bookingSettingsSetupData.createBookingSettings();

        alreadySetup = true;
    }
}