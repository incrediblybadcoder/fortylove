package ch.fortylove.configuration.setupdata;

import ch.fortylove.configuration.setupdata.data.BookingSettingsSetupData;
import ch.fortylove.configuration.setupdata.data.PlayerStatusSetupData;
import ch.fortylove.configuration.setupdata.data.PrivilegeSetupData;
import ch.fortylove.configuration.setupdata.data.RoleSetupData;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;


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