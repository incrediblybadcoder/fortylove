package ch.fortylove.devsetupdata.data;

import ch.fortylove.devsetupdata.DevSetupData;
import ch.fortylove.persistence.dto.PrivilegeDTO;
import ch.fortylove.persistence.dto.RoleDTO;
import ch.fortylove.persistence.entity.PrivilegeEntity;
import ch.fortylove.persistence.entity.RoleEntity;
import ch.fortylove.persistence.service.PrivilegeService;
import ch.fortylove.persistence.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DevSetupData
public class RoleSetupData {

    @Nonnull private final RoleService roleService;
    @Nonnull private final PrivilegeService privilegeService;

    @Autowired
    public RoleSetupData(@Nonnull final RoleService roleService,
                         @Nonnull final PrivilegeService privilegeService) {
        this.roleService = roleService;
        this.privilegeService = privilegeService;
    }

    public void createRoles() {
        createRoleIfNotFound(RoleEntity.ROLE_ADMIN, getAdminPrivileges());
        createRoleIfNotFound(RoleEntity.ROLE_STAFF, getStaffPrivileges());
        createRoleIfNotFound(RoleEntity.ROLE_USER, getUserPrivileges());
    }

    @Nonnull
    private List<PrivilegeDTO> getUserPrivileges() {
        final ArrayList<PrivilegeDTO> privileges = new ArrayList<>();
        final Optional<PrivilegeDTO> readPrivilege = privilegeService.findByName(PrivilegeEntity.READ_PRIVILEGE);
        final Optional<PrivilegeDTO> changePasswordPrivilege = privilegeService.findByName(PrivilegeEntity.CHANGE_PASSWORD_PRIVILEGE);

        readPrivilege.ifPresent(privileges::add);
        changePasswordPrivilege.ifPresent(privileges::add);

        return privileges;
    }

    @Nonnull
    private List<PrivilegeDTO> getStaffPrivileges() {
        return getAdminPrivileges();
    }

    @Nonnull
    private List<PrivilegeDTO> getAdminPrivileges() {
        final ArrayList<PrivilegeDTO> privileges = new ArrayList<>();
        final Optional<PrivilegeDTO> readPrivilege = privilegeService.findByName(PrivilegeEntity.READ_PRIVILEGE);
        final Optional<PrivilegeDTO> changePasswordPrivilege = privilegeService.findByName(PrivilegeEntity.CHANGE_PASSWORD_PRIVILEGE);
        final Optional<PrivilegeDTO> writePrivilege = privilegeService.findByName(PrivilegeEntity.WRITE_PRIVILEGE);

        readPrivilege.ifPresent(privileges::add);
        writePrivilege.ifPresent(privileges::add);
        changePasswordPrivilege.ifPresent(privileges::add);

        return privileges;
    }

    @Transactional
    void createRoleIfNotFound(@Nonnull final String name,
                              @Nonnull final List<PrivilegeDTO> privileges) {
        final Optional<RoleDTO> role = roleService.findByName(name);

        if (role.isEmpty()) {
            roleService.create(new RoleDTO(0L, name, null, privileges));
        }
    }
}