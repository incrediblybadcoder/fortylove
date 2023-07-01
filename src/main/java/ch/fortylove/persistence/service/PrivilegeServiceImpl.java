package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Nonnull private final PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeServiceImpl(@Nonnull final PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Nonnull
    @Override
    public Optional<Privilege> findById(final long id) {
       return privilegeRepository.findById(id);
    }

    @Nonnull
    @Override
    public Optional<Privilege> findByName(@Nonnull final String name) {
        return Optional.ofNullable(privilegeRepository.findByName(name));
    }

    @Nonnull
    @Override
    public Privilege create(@Nonnull final Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    @Override
    public void deleteById(final long id) {
        privilegeRepository.deleteById(id);
    }
}
