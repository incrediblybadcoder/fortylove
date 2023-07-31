package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.Role;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    @Nullable
    Role findByName(@Nonnull final String name);

    @Nonnull
    @Query("SELECT r FROM roles r WHERE r.name IN (:names)")
    List<Role> findRolesByNames(@Param("names") @Nonnull final List<String> names);
}
