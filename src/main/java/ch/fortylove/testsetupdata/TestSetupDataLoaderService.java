package ch.fortylove.testsetupdata;

import ch.fortylove.testsetupdata.data.BookingSetupData;
import ch.fortylove.testsetupdata.data.CourtSetupData;
import ch.fortylove.testsetupdata.data.PrivilegeSetupData;
import ch.fortylove.testsetupdata.data.RoleSetupData;
import ch.fortylove.testsetupdata.data.UserSetupData;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@TestSetupData
public class TestSetupDataLoaderService {

    @Nonnull private final UserSetupData userSetupData;
    @Nonnull private final RoleSetupData roleSetupData;
    @Nonnull private final PrivilegeSetupData privilegeSetupData;
    @Nonnull private final CourtSetupData courtSetupData;
    @Nonnull private final BookingSetupData bookingSetupData;

    @Autowired
    public TestSetupDataLoaderService(@Nonnull final UserSetupData userSetupData,
                                      @Nonnull final RoleSetupData roleSetupData,
                                      @Nonnull final PrivilegeSetupData privilegeSetupData,
                                      @Nonnull final CourtSetupData courtSetupData,
                                      @Nonnull final BookingSetupData bookingSetupData) {
        this.userSetupData = userSetupData;
        this.roleSetupData = roleSetupData;
        this.privilegeSetupData = privilegeSetupData;
        this.courtSetupData = courtSetupData;
        this.bookingSetupData = bookingSetupData;
    }

    public void initData() {
        privilegeSetupData.createPrivileges();
        roleSetupData.createRoles();
        userSetupData.createUsers();
        courtSetupData.createCourts();
        bookingSetupData.createBookings();
    }
}