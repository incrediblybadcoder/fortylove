package ch.fortylove.service;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.persistence.error.RecordNotFoundException;
import ch.fortylove.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Nonnull private final UserRepository userRepository;

    @Autowired
    public UserService(@Nonnull final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Nonnull
    public User create(@Nonnull final User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new DuplicateRecordException(user);
        }
        return userRepository.save(user);
    }

    @Nonnull
    public User update(@Nonnull final User user) {
        if (userRepository.findById(user.getId()).isEmpty()) {
            throw new RecordNotFoundException(user);
        }
        return userRepository.save(user);
    }

    @Nonnull
    public Optional<User> findByEmail(@Nonnull final String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Nonnull
    public Optional<User> findById(@Nonnull final Long id) {
        return userRepository.findById((id));
    }

    @Nonnull
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Nonnull
    public List<User> findAll(@Nonnull final String filterText) {
        if (filterText.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.search(filterText);
        }
    }

    public void delete(final long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        userRepository.delete(user);
    }

    @Nonnull
    public List<User> getPossibleBookingOpponents(@Nonnull final User currentUser) {
        final List<User> users = userRepository.findAllEnabledWithAvailableBookingsPerDay();
        users.remove(currentUser);
        return users;
    }
}
