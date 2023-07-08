package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Nullable
    User findByEmail(@Nonnull final String email);

    @Query("select u from users u " +
            "where lower(u.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(u.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<User> search(@Param("searchTerm") String searchTerm);

    @Query("SELECT u FROM users u WHERE u.playerStatus.bookingsPerDay > 0 AND u.enabled = true")
    List<User> findAllEnabledWithAvailableBookingsPerDay();
}
