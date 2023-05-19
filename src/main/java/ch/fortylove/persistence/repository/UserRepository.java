package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(@Nonnull final String email);
}
