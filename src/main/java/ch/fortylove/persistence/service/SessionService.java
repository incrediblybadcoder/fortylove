package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.UserDTO;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface SessionService {

    @Nonnull
    Optional<UserDTO> getCurrentUser();
}
