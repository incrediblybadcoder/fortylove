package ch.fortylove.configuration.devsetupdata;

import ch.fortylove.configuration.devsetupdata.data.BookingDevSetupData;
import ch.fortylove.configuration.devsetupdata.data.CourtDevSetupData;
import ch.fortylove.configuration.devsetupdata.data.UserDevSetupData;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;


@DevSetupData
public class DevSetupDataLoaderService {

    @Nonnull private final CourtDevSetupData courtDevSetupData;
    @Nonnull private final UserDevSetupData userDevSetupData;
    @Nonnull private final BookingDevSetupData bookingDevSetupData;

    @Autowired
    public DevSetupDataLoaderService(@Nonnull final CourtDevSetupData courtDevSetupData,
                                     @Nonnull final UserDevSetupData userDevSetupData,
                                     @Nonnull final BookingDevSetupData bookingDevSetupData) {
        this.courtDevSetupData = courtDevSetupData;
        this.userDevSetupData = userDevSetupData;
        this.bookingDevSetupData = bookingDevSetupData;
    }

    public void initData() {
        courtDevSetupData.createCourts();
        userDevSetupData.createUsers();
        bookingDevSetupData.createBookings();
    }
}