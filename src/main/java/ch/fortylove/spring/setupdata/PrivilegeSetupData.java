package ch.fortylove.spring.setupdata;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

@Component
@Profile("!production")
public class PrivilegeSetupData {

    @Nonnull private final PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeSetupData(@Nonnull final PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    public void createPrivileges() {
        createPrivilegeIfNotFound(Privilege.READ_PRIVILEGE);
        createPrivilegeIfNotFound(Privilege.WRITE_PRIVILEGE);
        createPrivilegeIfNotFound(Privilege.CHANGE_PASSWORD_PRIVILEGE);
    }

    @Transactional
    void createPrivilegeIfNotFound(@Nonnull final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
    }
}