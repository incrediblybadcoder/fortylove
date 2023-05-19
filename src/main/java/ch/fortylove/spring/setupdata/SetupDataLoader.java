package ch.fortylove.spring.setupdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

@Component
@Profile("!production")
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserSetupData userSetupData;
    private final RoleSetupData roleSetupData;
    private final PrivilegeSetupData privilegeSetupData;

    private boolean alreadySetup = false;

    @Autowired
    public SetupDataLoader(@Nonnull final UserSetupData userSetupData,
                           @Nonnull final RoleSetupData roleSetupData,
                           @Nonnull final PrivilegeSetupData privilegeSetupData) {
        this.userSetupData = userSetupData;
        this.roleSetupData = roleSetupData;
        this.privilegeSetupData = privilegeSetupData;
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

        alreadySetup = true;
    }
}