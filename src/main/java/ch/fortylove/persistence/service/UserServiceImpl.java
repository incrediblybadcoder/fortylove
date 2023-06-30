package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public User create(@Nonnull final User user) {
        return userRepository.save(user);
    }

    @Nonnull
    @Override
    public User update(@Nonnull final User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("User id must not be null");
    }

    @Nonnull
    @Override
    public Optional<User> findByEmail(@Nonnull final String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Nonnull
    @Override
    public Optional<User> findById(@Nonnull final Long id) {
        return userRepository.findById((id));
    }

    @Nonnull
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Nonnull
    @Override
    public List<User> findAll(@Nonnull final String filterText) {
        if (filterText.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.search(filterText);
        }
    }

    /*
        * Delete given user from database and all its bookings (see @Transactional and @ManyToMany relationship with CascadeType.ALL)
        * @param user to delete
     */
    @Override
    public void delete(@Nonnull final User user) {
        userRepository.delete(user);
    }
}
