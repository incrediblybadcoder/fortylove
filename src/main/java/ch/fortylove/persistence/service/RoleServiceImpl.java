package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.Role;
import ch.fortylove.persistence.dto.mapper.CycleAvoidingMappingContext;
import ch.fortylove.persistence.dto.mapper.RoleMapper;
import ch.fortylove.persistence.entity.RoleEntity;
import ch.fortylove.persistence.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Nonnull private final RoleRepository roleRepository;
    @Nonnull private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(@Nonnull final RoleRepository roleRepository,
                           @Nonnull final RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Nonnull
    @Override
    public Role create(@Nonnull final Role role) {
        final RoleEntity roleEntity = roleRepository.save(roleMapper.convert(role, new CycleAvoidingMappingContext()));
        return roleMapper.convert(roleEntity, new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public Optional<Role> findByName(@Nonnull final String name) {
        return Optional.ofNullable(roleMapper.convert(roleRepository.findByName(name), new CycleAvoidingMappingContext()));
    }
}
