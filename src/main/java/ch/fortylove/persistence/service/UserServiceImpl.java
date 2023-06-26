package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.UserDTO;
import ch.fortylove.persistence.dto.mapper.CycleAvoidingMappingContext;
import ch.fortylove.persistence.dto.mapper.UserMapper;
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
    @Nonnull private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(@Nonnull final UserRepository userRepository,
                           @Nonnull final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Nonnull
    @Override
    public UserDTO create(@Nonnull final UserDTO user) {
        final User save = userRepository.save(userMapper.convert(user, new CycleAvoidingMappingContext()));
        return userMapper.convert(save, new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public Optional<UserDTO> findByEmail(@Nonnull final String email) {
        return Optional.ofNullable(userMapper.convert(userRepository.findByEmail(email), new CycleAvoidingMappingContext()));
    }

    @Nonnull
    @Override
    public List<UserDTO> findAll() {
        return userMapper.convert(userRepository.findAll(), new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public List<UserDTO> findAll(@Nonnull final String filterText) {
        if (filterText.isEmpty()) {
            return userMapper.convert(userRepository.findAll(), new CycleAvoidingMappingContext());
        } else {
            return userMapper.convert(userRepository.search(filterText), new CycleAvoidingMappingContext());
        }
    }

    @Override
    public void save(@Nonnull final UserDTO user) {
        userRepository.save(userMapper.convert(user, new CycleAvoidingMappingContext()));
    }

    /*
        * Delete given user from database and all its bookings (see @Transactional and @ManyToMany relationship with CascadeType.ALL)
        * @param user to delete
     */
    @Override
    public void delete(@Nonnull final UserDTO user) {
        userRepository.delete(userMapper.convert(user, new CycleAvoidingMappingContext()));
    }
}
