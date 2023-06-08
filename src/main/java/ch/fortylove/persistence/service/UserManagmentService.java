package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
public interface UserManagmentService {

    @Nonnull
    boolean deleteUserAndAllItsBookings(@Nonnull User user);
}


