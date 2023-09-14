package ch.fortylove.configuration.setupdata;

import ch.fortylove.configuration.setupdata.data.BookingSettingsSetupData;
import ch.fortylove.configuration.setupdata.data.DefaultUserSetupData;
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
    @Nonnull private final DefaultUserSetupData defaultUserSetupData;

    @Autowired
    public SetupDataLoaderService(@Nonnull final BookingSettingsSetupData bookingSettingsSetupData,
                                  @Nonnull final PrivilegeSetupData privilegeSetupData,
                                  @Nonnull final RoleSetupData roleSetupData,
                                  @Nonnull final PlayerStatusSetupData playerStatusSetupData,
                                  @Nonnull final DefaultUserSetupData defaultUserSetupData) {
        this.bookingSettingsSetupData = bookingSettingsSetupData;
        this.privilegeSetupData = privilegeSetupData;
        this.roleSetupData = roleSetupData;
        this.playerStatusSetupData = playerStatusSetupData;
        this.defaultUserSetupData = defaultUserSetupData;
    }

    public void initData() {
        bookingSettingsSetupData.createData();
        privilegeSetupData.createData();
        roleSetupData.createData();
        playerStatusSetupData.createData();
        defaultUserSetupData.createData();
    }
}