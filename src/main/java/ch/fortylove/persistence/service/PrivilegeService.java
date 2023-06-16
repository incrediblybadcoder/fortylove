package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.Privilege;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface PrivilegeService {

    @Nonnull
    List<Privilege> findAll();

    @Nonnull
    Optional<Privilege> findById(final long id);

    @Nonnull
    Optional<Privilege> findByName(@Nonnull final String name);

    @Nonnull
    Privilege create(@Nonnull final Privilege privilege);

    void deleteById(final long id);

    @Nonnull
    Optional<Privilege> update(final long id, @Nonnull final Privilege privilege);
}
