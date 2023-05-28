package ch.fortylove.spring.setupdata;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.repository.PrivilegeRepository;
import ch.fortylove.persistence.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;

@Component
@Profile("!production")
public class RoleSetupData {

    @Nonnull private final RoleRepository roleRepository;
    @Nonnull private final PrivilegeRepository privilegeRepository;

    @Autowired
    public RoleSetupData(@Nonnull final RoleRepository roleRepository,
                         @Nonnull final PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    public void createRoles() {
        createRoleIfNotFound(Role.ROLE_ADMIN, getAdminPrivileges());
        createRoleIfNotFound(Role.ROLE_STAFF, getStaffPrivileges());
        createRoleIfNotFound(Role.ROLE_USER, getUserPrivileges());
    }

    @Nonnull
    private Collection<Privilege> getUserPrivileges() {
        final Privilege readPrivilege = privilegeRepository.findByName(Privilege.READ_PRIVILEGE);
        final Privilege changePasswordPrivilege = privilegeRepository.findByName(Privilege.CHANGE_PASSWORD_PRIVILEGE);

        return Arrays.asList(readPrivilege, changePasswordPrivilege);
    }

    @Nonnull
    private Collection<Privilege> getStaffPrivileges() {
        return getAdminPrivileges();
    }

    @Nonnull
    private Collection<Privilege> getAdminPrivileges() {
        final Privilege readPrivilege = privilegeRepository.findByName(Privilege.READ_PRIVILEGE);
        final Privilege writePrivilege = privilegeRepository.findByName(Privilege.WRITE_PRIVILEGE);
        final Privilege changePasswordPrivilege = privilegeRepository.findByName(Privilege.CHANGE_PASSWORD_PRIVILEGE);

        return Arrays.asList(readPrivilege, writePrivilege, changePasswordPrivilege);
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