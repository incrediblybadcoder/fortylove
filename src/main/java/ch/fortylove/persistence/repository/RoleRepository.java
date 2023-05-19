package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Nonnull
    Role findByName(@Nonnull final String name);
}
