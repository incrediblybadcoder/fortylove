package ch.fortylove.persistence.setupdata;

import ch.fortylove.persistence.setupdata.settings.BookingSettingsSetupData;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@SetupData
public class SetupDataLoaderService {

    @Nonnull private final BookingSettingsSetupData bookingSettingsSetupData;

    @Autowired
    public SetupDataLoaderService(@Nonnull final BookingSettingsSetupData bookingSettingsSetupData) {
        this.bookingSettingsSetupData = bookingSettingsSetupData;
    }

    public void initData() {
        bookingSettingsSetupData.createBookingSettings();
    }
}