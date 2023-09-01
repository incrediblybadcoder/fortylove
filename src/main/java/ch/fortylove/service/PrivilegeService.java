package ch.fortylove.service;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.repository.PrivilegeRepository;
import ch.fortylove.service.util.DatabaseResult;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PrivilegeService {

    @Nonnull private final PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeService(@Nonnull final PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Nonnull
    public Optional<Privilege> findById(@Nonnull final UUID id) {
       return privilegeRepository.findById(id);
    }

    @Nonnull
    public Optional<Privilege> findByName(@Nonnull final String name) {
        return Optional.ofNullable(privilegeRepository.findByName(name));
    }

    @Nonnull
    public DatabaseResult<Privilege> create(@Nonnull final Privilege privilege) {
        if (privilegeRepository.findById(privilege.getId()).isPresent()) {
            return new DatabaseResult<>("Privilege existiert bereits: " + privilege.getIdentifier());
        }
        return new DatabaseResult<>(privilegeRepository.save(privilege));
    }

    @Nonnull
    public DatabaseResult<UUID> delete(@Nonnull final UUID id) {
        final Optional<Privilege> privilegeOptional = privilegeRepository.findById(id);
        if (privilegeOptional.isEmpty()) {
            return new DatabaseResult<>("Privileg existiert nicht: " + id);
        }

        final Privilege privilege = privilegeOptional.get();
        privilegeRepository.delete(privilege);
        return new DatabaseResult<>(id);
    }
}
