package ch.fortylove.service;

import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.persistence.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Nonnull private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(@Nonnull final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Nonnull
    @Override
    public Role create(@Nonnull final Role role) {
        if (roleRepository.findById(role.getId()).isPresent()) {
            throw new DuplicateRecordException(role);
        }
        return roleRepository.save(role);
    }

    @Nonnull
    @Override
    public Optional<Role> findByName(@Nonnull final String name) {
        return Optional.ofNullable(roleRepository.findByName(name));
    }
}
