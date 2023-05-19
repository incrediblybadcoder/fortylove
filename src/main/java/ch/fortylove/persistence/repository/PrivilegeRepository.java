package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    @Nonnull
    Privilege findByName(@Nonnull final String name);
}
