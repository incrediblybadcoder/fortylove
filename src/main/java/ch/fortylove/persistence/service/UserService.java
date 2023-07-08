package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.User;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface UserService {

    @Nonnull
    User create(@Nonnull final User user);

    @Nonnull
    User update(@Nonnull final User user);

    @Nonnull
    Optional<User> findByEmail(@Nonnull final String email);

    @Nonnull
    Optional<User> findById(@Nonnull final Long id);

    @Nonnull
    List<User> findAll();

    @Nonnull
    List<User> findAll(@Nonnull final String filterText);

    void delete(final long id);

    boolean isUserAllowedToModifyBooking(@Nonnull final User user, @Nonnull final Booking booking);

    @Nonnull
    List<User> getPossibleBookingOpponents();
}
