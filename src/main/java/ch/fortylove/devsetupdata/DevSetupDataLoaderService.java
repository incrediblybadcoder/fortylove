package ch.fortylove.devsetupdata;

import ch.fortylove.devsetupdata.data.BookingSetupData;
import ch.fortylove.devsetupdata.data.CourtSetupData;
import ch.fortylove.devsetupdata.data.UserSetupData;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@DevSetupData
public class DevSetupDataLoaderService {

    @Nonnull private final CourtSetupData courtSetupData;
    @Nonnull private final UserSetupData userSetupData;
    @Nonnull private final BookingSetupData bookingSetupData;

    @Autowired
    public DevSetupDataLoaderService(@Nonnull final CourtSetupData courtSetupData,
                                     @Nonnull final UserSetupData userSetupData,
                                     @Nonnull final BookingSetupData bookingSetupData) {
        this.courtSetupData = courtSetupData;
        this.userSetupData = userSetupData;
        this.bookingSetupData = bookingSetupData;
    }

    public void initData() {
        courtSetupData.createCourts();
        userSetupData.createUsers();
        bookingSetupData.createBookings();
    }
}