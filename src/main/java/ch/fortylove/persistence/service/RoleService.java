package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.RoleDTO;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface RoleService {

    @Nonnull
    RoleDTO create(@Nonnull final RoleDTO role);

    @Nonnull
    Optional<RoleDTO> findByName(@Nonnull final String name);
}