package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.PrivilegeDTO;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface PrivilegeService {

    @Nonnull
    List<PrivilegeDTO> findAll();

    @Nonnull
    Optional<PrivilegeDTO> findById(final long id);

    @Nonnull
    Optional<PrivilegeDTO> findByName(@Nonnull final String name);

    @Nonnull
    PrivilegeDTO create(@Nonnull final PrivilegeDTO privilege);

    void deleteById(final long id);

    @Nonnull
    Optional<PrivilegeDTO> update(final long id, @Nonnull final PrivilegeDTO privilege);
}
