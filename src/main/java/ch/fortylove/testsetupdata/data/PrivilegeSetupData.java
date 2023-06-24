package ch.fortylove.testsetupdata.data;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.service.PrivilegeService;
import ch.fortylove.testsetupdata.TestSetupData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Optional;

@TestSetupData
public class PrivilegeSetupData {

    @Nonnull private final PrivilegeService privilegeService;

    @Autowired
    public PrivilegeSetupData(@Nonnull final PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    public void createPrivileges() {
        createPrivilegeIfNotFound(Privilege.READ_PRIVILEGE);
        createPrivilegeIfNotFound(Privilege.WRITE_PRIVILEGE);
        createPrivilegeIfNotFound(Privilege.CHANGE_PASSWORD_PRIVILEGE);
    }

    @Transactional
    void createPrivilegeIfNotFound(@Nonnull final String name) {
        final Optional<Privilege> privilege = privilegeService.findByName(name);
        if (privilege.isEmpty()) {
            privilegeService.create(new Privilege(name));
        }
    }
}