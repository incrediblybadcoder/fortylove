package ch.fortylove.devsetupdata.data;

import ch.fortylove.devsetupdata.DevSetupData;
import ch.fortylove.persistence.dto.PrivilegeDTO;
import ch.fortylove.persistence.entity.PrivilegeEntity;
import ch.fortylove.persistence.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Optional;

@DevSetupData
public class PrivilegeSetupData {

    @Nonnull private final PrivilegeService privilegeService;

    @Autowired
    public PrivilegeSetupData(@Nonnull final PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    public void createPrivileges() {
        createPrivilegeIfNotFound(PrivilegeEntity.READ_PRIVILEGE);
        createPrivilegeIfNotFound(PrivilegeEntity.WRITE_PRIVILEGE);
        createPrivilegeIfNotFound(PrivilegeEntity.CHANGE_PASSWORD_PRIVILEGE);
    }

    @Transactional
    void createPrivilegeIfNotFound(@Nonnull final String name) {
        final Optional<PrivilegeDTO> privilege = privilegeService.findByName(name);
        if (privilege.isEmpty()) {
            privilegeService.create(new PrivilegeDTO(0L, name, null));
        }
    }
}