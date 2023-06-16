package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.User;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface UserService {

    @Nonnull
    User create(@Nonnull final User user);

    @Nonnull
    Optional<User> findByEmail(@Nonnull final String email);

    @Nonnull
    List<User> findAll();

    @Nonnull
    List<User> findAll(@Nonnull final String filterText);

    void save(User user);

    @Transactional
    void delete(User user);
}
