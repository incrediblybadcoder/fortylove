package ch.fortylove.spring.setupdata;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Optional;

@Component
@Profile("!production")
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
        final Optional<Privilege> privilegeOptional = privilegeService.findByName(name);
        if (privilegeOptional.isEmpty()) {
            privilegeService.create(new Privilege(name));
        }
    }
}