package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.User;
import ch.fortylove.persistence.dto.UserFormInformations;
import ch.fortylove.persistence.dto.mapper.CycleAvoidingMappingContext;
import ch.fortylove.persistence.dto.mapper.UserMapper;
import ch.fortylove.persistence.entity.UserEntity;
import ch.fortylove.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public User create(@Nonnull final User user) {
        final UserEntity userEntity = userRepository.save(userMapper.convert(user, new CycleAvoidingMappingContext()));
        return userMapper.convert(userEntity, new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public Optional<User> findByEmail(@Nonnull final String email) {
        return Optional.ofNullable(userMapper.convert(userRepository.findByEmail(email), new CycleAvoidingMappingContext()));
    }

    @Nonnull
    @Override
    public List<User> findAll() {
        return userMapper.convert(userRepository.findAll(), new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public List<User> findAll(@Nonnull final String filterText) {
        if (filterText.isEmpty()) {
            return userMapper.convert(userRepository.findAll(), new CycleAvoidingMappingContext());
        } else {
            return userMapper.convert(userRepository.search(filterText), new CycleAvoidingMappingContext());
        }
    }

    @Override
    public void save(@Nonnull final User user) {
        userRepository.save(userMapper.convert(user, new CycleAvoidingMappingContext()));
    }

    @Override
    public User createUser(@Nonnull final User user) {
        final UserEntity save = userRepository.save(userMapper.convert(user, new CycleAvoidingMappingContext()));
        return userMapper.convert(save, new CycleAvoidingMappingContext());
    }

    @Override
    public User updateUser(@Nonnull final UserFormInformations user) {
        final UserEntity byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail == null) {
            throw new EntityNotFoundException("User not found");
        }
        else {
            if(user.getFirstName() != null) {
                byEmail.setFirstName(user.getFirstName());
            }

            if(user.getLastName() != null){
                byEmail.setLastName(user.getLastName());
            }

        }
        final UserEntity save = userRepository.save(byEmail);
        return userMapper.convert(save, new CycleAvoidingMappingContext());
    }

    /*
        * Delete given user from database and all its bookings (see @Transactional and @ManyToMany relationship with CascadeType.ALL)
        * @param user to delete
     */
    @Override
    public void delete(@Nonnull final User user) {
        userRepository.delete(userMapper.convert(user, new CycleAvoidingMappingContext()));
    }
}
