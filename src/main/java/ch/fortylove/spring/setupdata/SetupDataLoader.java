package ch.fortylove.spring.setupdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

@Component
@Profile({"h2", "develop", "local"})
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Nonnull private final UserSetupData userSetupData;
    @Nonnull private final RoleSetupData roleSetupData;
    @Nonnull private final PrivilegeSetupData privilegeSetupData;
    @Nonnull private final CourtSetupData courtSetupData;
    @Nonnull private final BookingSetupData bookingSetupData;

    private boolean alreadySetup = false;

    @Autowired
    public SetupDataLoader(@Nonnull final UserSetupData userSetupData,
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

    @Override
    @Transactional
    public void onApplicationEvent(@Nonnull final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        privilegeSetupData.createPrivileges();
        roleSetupData.createRoles();
        userSetupData.createUsers();
        courtSetupData.createCourts();
        bookingSetupData.createBookings();

        alreadySetup = true;
    }
}