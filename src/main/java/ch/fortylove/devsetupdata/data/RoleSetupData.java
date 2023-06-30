package ch.fortylove.devsetupdata.data;

import ch.fortylove.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
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
        createRoleIfNotFound(Role.ROLE_ADMIN, getAdminPrivileges());
        createRoleIfNotFound(Role.ROLE_STAFF, getStaffPrivileges());
        createRoleIfNotFound(Role.ROLE_USER, getUserPrivileges());
    }

    @Nonnull
    private List<Privilege> getUserPrivileges() {
        final ArrayList<Privilege> Privileges = new ArrayList<>();
        final Optional<Privilege> readPrivilege = privilegeService.findByName(Privilege.READ_PRIVILEGE);
        final Optional<Privilege> changePasswordPrivilege = privilegeService.findByName(Privilege.CHANGE_PASSWORD_PRIVILEGE);

        readPrivilege.ifPresent(Privileges::add);
        changePasswordPrivilege.ifPresent(Privileges::add);

        return Privileges;
    }

    @Nonnull
    private List<Privilege> getStaffPrivileges() {
        return getAdminPrivileges();
    }

    @Nonnull
    private List<Privilege> getAdminPrivileges() {
        final ArrayList<Privilege> Privileges = new ArrayList<>();
        final Optional<Privilege> readPrivilege = privilegeService.findByName(Privilege.READ_PRIVILEGE);
        final Optional<Privilege> changePasswordPrivilege = privilegeService.findByName(Privilege.CHANGE_PASSWORD_PRIVILEGE);
        final Optional<Privilege> writePrivilege = privilegeService.findByName(Privilege.WRITE_PRIVILEGE);

        readPrivilege.ifPresent(Privileges::add);
        writePrivilege.ifPresent(Privileges::add);
        changePasswordPrivilege.ifPresent(Privileges::add);

        return Privileges;
    }

    @Transactional
    void createRoleIfNotFound(@Nonnull final String name,
                              @Nonnull final List<Privilege> Privileges) {
        final Optional<Role> role = roleService.findByName(name);

        if (role.isEmpty()) {
            roleService.create(new Role(0L, name, null, Privileges));
        }
    }
}