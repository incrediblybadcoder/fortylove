package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.UnvalidatedUser;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UnvalidatedUserRepository extends JpaRepository<UnvalidatedUser, UUID> {
    @Nullable
    UnvalidatedUser findByActivationCode(String activationCode);

    @Nullable
    UnvalidatedUser findByEmail(@Nonnull final String email);
}
