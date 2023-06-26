package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.Role;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface RoleService {

    @Nonnull
    Role create(@Nonnull final Role role);

    @Nonnull
    Optional<Role> findByName(@Nonnull final String name);
}