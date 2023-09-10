package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.User;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Nullable
    User findByEmail(@Nonnull final String email);

    @Query("SELECT u FROM users u WHERE u.playerStatus.bookingsPerDay > 0 AND u.enabled = true")
    List<User> findAllEnabledWithAvailableBookingsPerDay();

    @Query("SELECT u FROM users u JOIN u.authenticationDetails ad WHERE ad.resetToken = :resetToken")
    User findByResetToken(@Param("resetToken") String resetToken);
}
