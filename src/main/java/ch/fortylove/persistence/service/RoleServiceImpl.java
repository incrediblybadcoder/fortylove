package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.RoleDTO;
import ch.fortylove.persistence.dto.mapper.CycleAvoidingMappingContext;
import ch.fortylove.persistence.dto.mapper.RoleMapper;
import ch.fortylove.persistence.entity.Role;
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
    public RoleDTO create(@Nonnull final RoleDTO roleDTO) {
        final Role role = roleRepository.save(roleMapper.convert(roleDTO, new CycleAvoidingMappingContext()));
        return roleMapper.convert(role, new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public Optional<RoleDTO> findByName(@Nonnull final String name) {
        return Optional.ofNullable(roleMapper.convert(roleRepository.findByName(name), new CycleAvoidingMappingContext()));
    }
}
