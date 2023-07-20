package ch.fortylove.configuration.setupdata.data;

import ch.fortylove.configuration.setupdata.SetupData;
import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.service.PrivilegeService;
import ch.fortylove.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SetupData
public class RoleSetupData {

    @Nonnull public static final String ROLE_ADMIN = "ROLE_ADMIN";
    @Nonnull public static final String ROLE_STAFF = "ROLE_STAFF";
    @Nonnull public static final String ROLE_USER = "ROLE_USER";

    @Nonnull private final RoleService roleService;
    @Nonnull private final PrivilegeService privilegeService;

    @Autowired
    public RoleSetupData(@Nonnull final RoleService roleService,
                         @Nonnull final PrivilegeService privilegeService) {
        this.roleService = roleService;
        this.privilegeService = privilegeService;
    }

    public void createRoles() {
        createRoleIfNotFound(ROLE_ADMIN, getAdminPrivileges());
        createRoleIfNotFound(ROLE_STAFF, getStaffPrivileges());
        createRoleIfNotFound(ROLE_USER, getUserPrivileges());
    }

    @Nonnull
    private Set<Privilege> getUserPrivileges() {
        final Set<Privilege> privileges = new HashSet<>();
        final Optional<Privilege> readPrivilege = privilegeService.findByName(PrivilegeSetupData.READ_PRIVILEGE);
        final Optional<Privilege> changePasswordPrivilege = privilegeService.findByName(PrivilegeSetupData.CHANGE_PASSWORD_PRIVILEGE);

        readPrivilege.ifPresent(privileges::add);
        changePasswordPrivilege.ifPresent(privileges::add);

        return privileges;
    }

    @Nonnull
    private Set<Privilege> getStaffPrivileges() {
        return getAdminPrivileges();
    }

    @Nonnull
    private Set<Privilege> getAdminPrivileges() {
        final Set<Privilege> privileges = new HashSet<>();
        final Optional<Privilege> readPrivilege = privilegeService.findByName(PrivilegeSetupData.READ_PRIVILEGE);
        final Optional<Privilege> changePasswordPrivilege = privilegeService.findByName(PrivilegeSetupData.CHANGE_PASSWORD_PRIVILEGE);
        final Optional<Privilege> writePrivilege = privilegeService.findByName(PrivilegeSetupData.WRITE_PRIVILEGE);

        readPrivilege.ifPresent(privileges::add);
        writePrivilege.ifPresent(privileges::add);
        changePasswordPrivilege.ifPresent(privileges::add);

        return privileges;
    }

    @Transactional
    private void createRoleIfNotFound(@Nonnull final String name,
                                      @Nonnull final Set<Privilege> privileges) {
        final Optional<Role> role = roleService.findByName(name);

        if (role.isEmpty()) {
            roleService.create(new Role(name, privileges));
        }
    }
}