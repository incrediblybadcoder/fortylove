package ch.fortylove.persistence.setupdata.data;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.service.PrivilegeService;
import ch.fortylove.persistence.setupdata.SetupData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Optional;

@SetupData
public class PrivilegeSetupData {

    @Nonnull public final static String READ_PRIVILEGE = "READ_PRIVILEGE";
    @Nonnull public final static String WRITE_PRIVILEGE = "WRITE_PRIVILEGE";
    @Nonnull public final static String CHANGE_PASSWORD_PRIVILEGE = "CHANGE_PASSWORD_PRIVILEGE";

    @Nonnull private final PrivilegeService privilegeService;

    @Autowired
    public PrivilegeSetupData(@Nonnull final PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    public void createPrivileges() {
        createPrivilegeIfNotFound(READ_PRIVILEGE);
        createPrivilegeIfNotFound(WRITE_PRIVILEGE);
        createPrivilegeIfNotFound(CHANGE_PASSWORD_PRIVILEGE);
    }

    @Transactional
    void createPrivilegeIfNotFound(@Nonnull final String name) {
        final Optional<Privilege> privilege = privilegeService.findByName(name);
        if (privilege.isEmpty()) {
            privilegeService.create(new Privilege(name));
        }
    }
}