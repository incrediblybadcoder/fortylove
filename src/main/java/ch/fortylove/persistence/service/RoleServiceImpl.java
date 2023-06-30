package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Nonnull private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(@Nonnull final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Nonnull
    @Override
    public Role create(@Nonnull final Role role) {
        return roleRepository.save(role);
    }

    @Nonnull
    @Override
    public Optional<Role> findByName(@Nonnull final String name) {
        return Optional.ofNullable(roleRepository.findByName(name));
    }
}
