package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.UserDTO;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface UserService {

    @Nonnull
    UserDTO create(@Nonnull final UserDTO user);

    @Nonnull
    Optional<UserDTO> findByEmail(@Nonnull final String email);

    @Nonnull
    List<UserDTO> findAll();

    @Nonnull
    List<UserDTO> findAll(@Nonnull final String filterText);

    void save(@Nonnull final UserDTO user);

    @Transactional
    void delete(@Nonnull final UserDTO user);
}
