package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.Privilege;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, UUID> {

    @Nullable
    Privilege findByName(@Nonnull final String name);
}
