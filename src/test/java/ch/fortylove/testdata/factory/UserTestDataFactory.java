package ch.fortylove.testdata.factory;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.service.UserService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

@SpringComponent
public class UserTestDataFactory {

    @Nonnull public static final String DEFAULT_USER = "default_user@fortylove.ch";

    @Nonnull private final UserService userService;
    @Nonnull private final RoleTestDataFactory roleTestDataFactory;
    @Nonnull private final PlayerStatusTestDataFactory playerStatusTestDataFactory;

    @Autowired
    public UserTestDataFactory(@Nonnull final UserService userService,
                               @Nonnull final RoleTestDataFactory roleTestDataFactory,
                               @Nonnull final PlayerStatusTestDataFactory playerStatusTestDataFactory) {
        this.userService = userService;
        this.roleTestDataFactory = roleTestDataFactory;
        this.playerStatusTestDataFactory = playerStatusTestDataFactory;
    }

    @Nonnull
    public User createUser(@Nonnull final String email) {
        final Set<Role> roles = Set.of(roleTestDataFactory.getDefault());
        final PlayerStatus playerStatus = playerStatusTestDataFactory.getDefault();
        return userService.create(new User("firstName", "lastName", email, "password", true, roles, playerStatus));
    }

    @Nonnull
    public User getDefault() {
        final Optional<User> defaultUser = userService.findByEmail(DEFAULT_USER);
        if (defaultUser.isEmpty()) {
            return createUser(DEFAULT_USER);
        }

        return defaultUser.get();
    }
}
