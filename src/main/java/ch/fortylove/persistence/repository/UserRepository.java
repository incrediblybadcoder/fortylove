package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Nullable
    User findByEmail(@Nonnull final String email);
}
