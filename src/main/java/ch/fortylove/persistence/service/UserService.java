package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    @Nonnull
    User create(@Nonnull final User user);

    @Nonnull
    Optional<User> findByEmail(@Nonnull final String email);

    @Nonnull
    List<User> findAll();

    @Nonnull
    List<User> findAll(String filterText);
}
