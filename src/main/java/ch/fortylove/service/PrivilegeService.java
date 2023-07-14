package ch.fortylove.service;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.persistence.error.RecordNotFoundException;
import ch.fortylove.persistence.repository.PrivilegeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
@Transactional
public class PrivilegeService {

    @Nonnull private final PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeService(@Nonnull final PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Nonnull
    public Optional<Privilege> findById(final long id) {
       return privilegeRepository.findById(id);
    }

    @Nonnull
    public Optional<Privilege> findByName(@Nonnull final String name) {
        return Optional.ofNullable(privilegeRepository.findByName(name));
    }

    @Nonnull
    public Privilege create(@Nonnull final Privilege privilege) {
        if (privilegeRepository.findById(privilege.getId()).isPresent()) {
            throw new DuplicateRecordException(privilege);
        }
        return privilegeRepository.save(privilege);
    }

    public void delete(final long id) {
        final Privilege privilege = privilegeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        privilegeRepository.delete(privilege);
    }
}
