package ch.fortylove.persistence.setupdata;

import ch.fortylove.persistence.setupdata.data.BookingSettingsSetupData;
import ch.fortylove.persistence.setupdata.data.PlayerStatusSetupData;
import ch.fortylove.persistence.setupdata.data.PrivilegeSetupData;
import ch.fortylove.persistence.setupdata.data.RoleSetupData;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@SetupData
public class SetupDataLoaderService {

    @Nonnull private final BookingSettingsSetupData bookingSettingsSetupData;
    @Nonnull private final PrivilegeSetupData privilegeSetupData;
    @Nonnull private final RoleSetupData roleSetupData;
    @Nonnull private final PlayerStatusSetupData playerStatusSetupData;

    @Autowired
    public SetupDataLoaderService(@Nonnull final BookingSettingsSetupData bookingSettingsSetupData,
                                  @Nonnull final PrivilegeSetupData privilegeSetupData,
                                  @Nonnull final RoleSetupData roleSetupData,
                                  @Nonnull final PlayerStatusSetupData playerStatusSetupData) {
        this.bookingSettingsSetupData = bookingSettingsSetupData;
        this.privilegeSetupData = privilegeSetupData;
        this.roleSetupData = roleSetupData;
        this.playerStatusSetupData = playerStatusSetupData;
    }

    public void initData() {
        bookingSettingsSetupData.createBookingSettings();
        privilegeSetupData.createPrivileges();
        roleSetupData.createRoles();
        playerStatusSetupData.createPlayerStatus();
    }
}