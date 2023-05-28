package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
public interface RoleService {

    @Nonnull
    Role create(@Nonnull final Role role);

    @Nonnull
    Optional<Role> findByName(@Nonnull final String name);
}