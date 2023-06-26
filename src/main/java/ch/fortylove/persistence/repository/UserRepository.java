package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Nullable
    UserEntity findByEmail(@Nonnull final String email);

    @Query("select u from users u " +
            "where lower(u.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(u.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<UserEntity> search(@Param("searchTerm") String searchTerm);
}
