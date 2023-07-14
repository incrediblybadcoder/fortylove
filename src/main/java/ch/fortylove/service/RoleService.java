package ch.fortylove.service;

import ch.fortylove.persistence.entity.Role;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface RoleService {

    @Nonnull
    Role create(@Nonnull final Role role);

    @Nonnull
    Optional<Role> findByName(@Nonnull final String name);

    @Nonnull
    List<Role> getDefaultNewUserRole();
}