package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.PrivilegeDTO;
import ch.fortylove.persistence.dto.mapper.CycleAvoidingMappingContext;
import ch.fortylove.persistence.dto.mapper.PrivilegeMapper;
import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.repository.PrivilegeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Nonnull private final PrivilegeRepository privilegeRepository;
    @Nonnull private final PrivilegeMapper privilegeMapper;

    @Autowired
    public PrivilegeServiceImpl(@Nonnull final PrivilegeRepository privilegeRepository,
                                @Nonnull final PrivilegeMapper privilegeMapper) {
        this.privilegeRepository = privilegeRepository;
        this.privilegeMapper = privilegeMapper;
    }

    @Nonnull
    @Override
    public List<PrivilegeDTO> findAll() {
        return privilegeMapper.convert(privilegeRepository.findAll(), new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public Optional<PrivilegeDTO> findById(final long id) {
        final Optional<Privilege> privilegeEntity = privilegeRepository.findById(id);
        //noinspection OptionalIsPresent
        return privilegeEntity.isPresent() ?
                Optional.of(privilegeMapper.convert(privilegeEntity.get(), new CycleAvoidingMappingContext())) :
                Optional.empty();
    }

    @Nonnull
    @Override
    public Optional<PrivilegeDTO> findByName(@Nonnull final String name) {
        final Privilege privilege = privilegeRepository.findByName(name);
        return Optional.ofNullable(privilegeMapper.convert(privilege, new CycleAvoidingMappingContext()));
    }

    @Nonnull
    @Override
    public PrivilegeDTO create(@Nonnull final PrivilegeDTO privilegeDTO) {
        final Privilege privilege = privilegeRepository.save(privilegeMapper.convert(privilegeDTO, new CycleAvoidingMappingContext()));
        return privilegeMapper.convert(privilege, new CycleAvoidingMappingContext());
    }

    @Override
    public void deleteById(final long id) {
        privilegeRepository.deleteById(id);
    }

    @Nonnull
    @Override
    public Optional<PrivilegeDTO> update(final long id,
                                         @Nonnull final PrivilegeDTO privilegeDTO) {
        final Optional<PrivilegeDTO> existingPrivilegeOptional = findById(id);

        if (existingPrivilegeOptional.isPresent()) {
            final PrivilegeDTO existingPrivilegeDTO = existingPrivilegeOptional.get();
            BeanUtils.copyProperties(privilegeDTO, existingPrivilegeDTO, "id");
            return Optional.of(create(existingPrivilegeDTO));
        } else {
            return Optional.empty();
        }
    }
}
