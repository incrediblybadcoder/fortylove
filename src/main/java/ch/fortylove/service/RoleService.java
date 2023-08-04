package ch.fortylove.service;

import ch.fortylove.configuration.setupdata.data.RoleSetupData;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.persistence.repository.RoleRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class RoleService {

    @Nonnull public static final String DEFAULT_ROLE_FOR_NEW_USER = RoleSetupData.ROLE_USER;

    @Nonnull private final RoleRepository roleRepository;

    @Autowired
    public RoleService(@Nonnull final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Nonnull
    public Role create(@Nonnull final Role role) {
        if (roleRepository.findById(role.getId()).isPresent()) {
            throw new DuplicateRecordException(role);
        }
        return roleRepository.save(role);
    }

    @Nonnull
    public Optional<Role> findByName(@Nonnull final String name) {
        return Optional.ofNullable(roleRepository.findByName(name));
    }

    @Nonnull
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Nonnull
    public Set<Role> getDefaultUserRoles() {
        @Nonnull final List<String> userRoles = RoleSetupData.getUserRoles();
        final List<Role> rolesByNames = roleRepository.findRolesByNames(userRoles);
        return new HashSet<>(rolesByNames);
    }

    @Nonnull
    public Set<Role> getDefaultManagementRoles() {
        @Nonnull final List<String> managementRoles = RoleSetupData.getManagementRoles();
        final List<Role> rolesByNames = roleRepository.findRolesByNames(managementRoles);
        return new HashSet<>(rolesByNames);
    }

    @Nonnull
    public Set<Role> getDefaultAdminRoles() {
        @Nonnull final List<String> managementRoles = RoleSetupData.getAdminRoles();
        final List<Role> rolesByNames = roleRepository.findRolesByNames(managementRoles);
        return new HashSet<>(rolesByNames);
    }

    @Nonnull
    public Set<Role> getDefaultStaffRoles() {
        @Nonnull final List<String> staffRoles = RoleSetupData.getStaffRoles();
        final List<Role> rolesByNames = roleRepository.findRolesByNames(staffRoles);
        return new HashSet<>(rolesByNames);

    }
}
