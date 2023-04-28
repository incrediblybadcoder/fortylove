package ch.fortylove.service;

import ch.fortylove.model.User;
import ch.fortylove.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

@Service
public class UserService {

    @Nonnull private final UserRepository userRepository;

    @Autowired
    public UserService(@Nonnull final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Nonnull
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Nonnull
    public User find(final long id) {
        return userRepository.getReferenceById(id);
    }

    @Nonnull
    public User create(@Nonnull final User user) {
        return userRepository.saveAndFlush(user);
    }

    public void delete(final long id) {
        userRepository.deleteById(id);
    }

    @Nonnull
    public User update(final long id, @Nonnull User user) {
        final User existingUser = userRepository.getReferenceById(id);
        BeanUtils.copyProperties(user, existingUser, "user_id");
        return userRepository.saveAndFlush(existingUser);
    }
}
