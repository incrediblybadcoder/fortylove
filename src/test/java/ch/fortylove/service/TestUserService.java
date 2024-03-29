package ch.fortylove.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.AuthenticationDetails;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.Theme;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.UserSettings;
import ch.fortylove.persistence.entity.UserStatus;
import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringTest
class TestUserService extends ServiceTest {

    @Nonnull private final UserService testee;

    @Nonnull private Role role;
    @Nonnull private PlayerStatus playerStatus;

    @Autowired
    public TestUserService(@Nonnull final UserService testee) {
        this.testee = testee;
    }

    @BeforeEach
    void setUp() {
        role = getTestDataFactory().getRoleDataFactory().getDefault();
        playerStatus = getTestDataFactory().getPlayerStatusDataFactory().getDefault();
    }

    @Test
    public void testCreate() {
        final User createdUser = testee.create(new User("firstName", "lastName", "email@fortylove.ch", getAuthenticationDetails(), UserStatus.MEMBER, true, Set.of(role), playerStatus, getUserSettings())).getData().get();

        final List<User> foundUser = testee.findAll();
        Assertions.assertEquals(1, foundUser.size());
        Assertions.assertEquals(createdUser, foundUser.get(0));
    }

    @Test
    public void testFindByEmail_notExists() {
        testee.create(new User("firstName1", "lastName1", "email1@fortylove.ch", getAuthenticationDetails(), UserStatus.MEMBER, true, Set.of(role), playerStatus, getUserSettings()));
        testee.create(new User("firstName3", "lastName3", "email3@fortylove.ch", getAuthenticationDetails(), UserStatus.MEMBER, true, Set.of(role), playerStatus, getUserSettings()));

        final Optional<User> foundUser = testee.findByEmail("email2@fortylove.ch");

        Assertions.assertTrue(foundUser.isEmpty());
    }

    @Test
    public void testFindByEmail_exists() {
        testee.create(new User("firstName1", "lastName1", "email1@fortylove.ch", getAuthenticationDetails(), UserStatus.MEMBER, true, Set.of(role), playerStatus, getUserSettings()));
        final User createdUser = testee.create(new User("firstName2", "lastName2", "email2@fortylove.ch", getAuthenticationDetails(), UserStatus.MEMBER, true, Set.of(role), playerStatus, getUserSettings())).getData().get();
        testee.create(new User("firstName3", "lastName3", "email3@fortylove.ch", getAuthenticationDetails(), UserStatus.MEMBER, true, Set.of(role), playerStatus, getUserSettings()));

        final Optional<User> foundUser = testee.findByEmail("email2@fortylove.ch");

        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(createdUser, foundUser.get());
    }

    @Nonnull
    private AuthenticationDetails getAuthenticationDetails() {
        return new AuthenticationDetails("password");
    }

    @Nonnull
    private UserSettings getUserSettings() {
        return new UserSettings(Theme.LIGHT, true);
    }
}