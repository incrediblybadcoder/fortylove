package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Nonnull private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(@Nonnull final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Nonnull
    public User create(@Nonnull final User user) {
        return userRepository.save(user);
    }

    @Nonnull
    @Override
    public Optional<User> findByEmail(@Nonnull final String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Nonnull
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Nonnull
    @Override
    public List<User> findAll(final String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.search(filterText);
        }
    }

    @Override
    public void save(final User user) {
        if (user == null) {
            System.err.println("User is null. Are you sure you have connected your form to the application?");
            return;
        }
        userRepository.save(user);
    }

    /*
        * Delete given user from database.
        * @param user to delete
        * @return always true (for now)
        * wirft folgende Exception (wenn z.B der User noch Buchungen hat), welche ich hier jedoch nicht catchen kann Caused by: org.hibernate.exception.ConstraintViolationException: could not execute statement [Referentielle Integrit�t verletzt: "FKG4ILJIMIUSMS42S25S87XPM9D: PUBLIC.BOOKINGS_USERS FOREIGN KEY(USER_ID) REFERENCES PUBLIC.USERS(ID) (CAST(4 AS BIGINT))"
        *
     */
    @Override
    @Transactional
    public boolean delete(final User user) {
        userRepository.delete(user);
        return true;
    }
}
