package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Long> {

    @Nullable
    PrivilegeEntity findByName(@Nonnull final String name);
}
