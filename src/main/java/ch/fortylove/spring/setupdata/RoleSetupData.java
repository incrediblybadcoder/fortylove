package ch.fortylove.spring.setupdata;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.repository.RoleRepository;
import ch.fortylove.persistence.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
@Profile("!production")
public class RoleSetupData {

    @Nonnull private final RoleRepository roleRepository;
    @Nonnull private final PrivilegeService privilegeService;

    @Autowired
    public RoleSetupData(@Nonnull final RoleRepository roleRepository,
                         @Nonnull final PrivilegeService privilegeService) {
        this.roleRepository = roleRepository;
        this.privilegeService = privilegeService;
    }

    public void createRoles() {
        createRoleIfNotFound(Role.ROLE_ADMIN, getAdminPrivileges());
        createRoleIfNotFound(Role.ROLE_STAFF, getStaffPrivileges());
        createRoleIfNotFound(Role.ROLE_USER, getUserPrivileges());
    }

    @Nonnull
    private Collection<Privilege> getUserPrivileges() {
        final Optional<Privilege> readPrivilege = privilegeService.findByName(Privilege.READ_PRIVILEGE);
        final Optional<Privilege> changePasswordPrivilege = privilegeService.findByName(Privilege.CHANGE_PASSWORD_PRIVILEGE);

        final ArrayList<Privilege> privileges = new ArrayList<>();
        readPrivilege.ifPresent(privileges::add);
        changePasswordPrivilege.ifPresent(privileges::add);

        return privileges;
    }

    @Nonnull
    private Collection<Privilege> getStaffPrivileges() {
        return getAdminPrivileges();
    }

    @Nonnull
    private Collection<Privilege> getAdminPrivileges() {
        final Optional<Privilege> readPrivilege = privilegeService.findByName(Privilege.READ_PRIVILEGE);
        final Optional<Privilege> changePasswordPrivilege = privilegeService.findByName(Privilege.CHANGE_PASSWORD_PRIVILEGE);
        final Optional<Privilege> writePrivilege = privilegeService.findByName(Privilege.WRITE_PRIVILEGE);

        final ArrayList<Privilege> privileges = new ArrayList<>();
        readPrivilege.ifPresent(privileges::add);
        writePrivilege.ifPresent(privileges::add);
        changePasswordPrivilege.ifPresent(privileges::add);

        return privileges;
    }

    @Transactional
    void createRoleIfNotFound(@Nonnull final String name,
                              @Nonnull final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
    }
}