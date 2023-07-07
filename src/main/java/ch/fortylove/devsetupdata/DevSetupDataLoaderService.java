package ch.fortylove.devsetupdata;

import ch.fortylove.devsetupdata.data.BookingSetupData;
import ch.fortylove.devsetupdata.data.CourtSetupData;
import ch.fortylove.devsetupdata.data.PlayerStatusSetupData;
import ch.fortylove.devsetupdata.data.PrivilegeSetupData;
import ch.fortylove.devsetupdata.data.RoleSetupData;
import ch.fortylove.devsetupdata.data.UserSetupData;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@DevSetupData
public class DevSetupDataLoaderService {

    @Nonnull private final UserSetupData userSetupData;
    @Nonnull private final RoleSetupData roleSetupData;
    @Nonnull private final PrivilegeSetupData privilegeSetupData;
    @Nonnull private final CourtSetupData courtSetupData;
    @Nonnull private final BookingSetupData bookingSetupData;

    @Nonnull private final PlayerStatusSetupData playerStatusSetupData;

    @Autowired
    public DevSetupDataLoaderService(@Nonnull final UserSetupData userSetupData,
                                     @Nonnull final RoleSetupData roleSetupData,
                                     @Nonnull final PrivilegeSetupData privilegeSetupData,
                                     @Nonnull final CourtSetupData courtSetupData,
                                     @Nonnull final BookingSetupData bookingSetupData,
                                     @Nonnull final PlayerStatusSetupData playerStatusService) {
        this.userSetupData = userSetupData;
        this.roleSetupData = roleSetupData;
        this.privilegeSetupData = privilegeSetupData;
        this.courtSetupData = courtSetupData;
        this.bookingSetupData = bookingSetupData;
        this.playerStatusSetupData = playerStatusService;
    }

    public void initData() {
        privilegeSetupData.createPrivileges();
        roleSetupData.createRoles();
        playerStatusSetupData.createPlayerStatus();
        userSetupData.createUsers();
        courtSetupData.createCourts();
        bookingSetupData.createBookings();
    }
}