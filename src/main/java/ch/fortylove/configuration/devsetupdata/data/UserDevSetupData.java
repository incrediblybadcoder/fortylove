package ch.fortylove.configuration.devsetupdata.data;

import ch.fortylove.configuration.devsetupdata.DevSetupData;
import ch.fortylove.configuration.setupdata.data.PlayerStatusSetupData;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.UserStatus;
import ch.fortylove.persistence.entity.factory.UserFactory;
import ch.fortylove.persistence.error.RecordNotFoundException;
import ch.fortylove.service.PlayerStatusService;
import ch.fortylove.service.RoleService;
import ch.fortylove.service.UserService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@DevSetupData
public class UserDevSetupData implements ch.fortylove.configuration.devsetupdata.data.DevSetupData {

    @Nonnull private final UserService userService;
    @Nonnull private final RoleService roleService;
    @Nonnull private final PlayerStatusService playerStatusService;
    @Nonnull private final UserFactory userFactory;

    @Autowired
    public UserDevSetupData(@Nonnull final UserService userService, @Nonnull final RoleService roleService,
                            @Nonnull final PlayerStatusService playerStatus,
                            @Nonnull final UserFactory userFactory) {
        this.userService = userService;
        this.roleService = roleService;
        this.playerStatusService = playerStatus;
        this.userFactory = userFactory;
    }

    @Override
    public void createDevData() {
        createUserIfNotFound("lukas.meier@yahoo.com", "Lukas", "Meier", "password", UserStatus.MEMBER, getAdminRole(), getPlayerStatus(PlayerStatusSetupData.TOURNAMENT), true);
        createUserIfNotFound("lena.müller@hotmail.com", "Lena", "Müller", "password", UserStatus.MEMBER, getStaffRole(), getPlayerStatus(PlayerStatusSetupData.TOURNAMENT), true);
        createUserIfNotFound("anna.schneider@gmail.com", "Anna", "Schneider", "password", UserStatus.GUEST, getUserRole(), getPlayerStatus(PlayerStatusSetupData.GUEST), true);
        createUserIfNotFound("marco.solombrino@gmail.com", "Marco", "Solombrino", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
        createUserIfNotFound("jonas.cahenzli@gmail.com", "Jonas", "Cahenzli", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
        createUserIfNotFound("david.huber@gmx.ch", "David", "Huber", "password", UserStatus.GUEST, getUserRole(), getPlayerStatus(PlayerStatusSetupData.GUEST), true);
        createUserIfNotFound("sara.frey@yahoo.com", "Sara", "Frey", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
        createUserIfNotFound("benjamin.graf@hotmail.com", "Benjamin", "Graf", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.PASSIVE), true);
        createUserIfNotFound("laura.fuchs@gmail.com", "Laura", "Fuchs", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.TOURNAMENT), true);
        createUserIfNotFound("simon.wirth@gmx.ch", "Simon", "Wirth", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.INACTIVE), false);
        createUserIfNotFound("sophie.gerber@yahoo.com", "Sophie", "Gerber", "password", UserStatus.GUEST, getUserRole(), getPlayerStatus(PlayerStatusSetupData.GUEST), true);
        createUserIfNotFound("martin.weiss@hotmail.com", "Martin", "Weiss", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
        createUserIfNotFound("maria.bianchi@yahoo.it", "Maria", "Bianchi", "password", UserStatus.GUEST_PENDING, getUserRole(), getPlayerStatus(PlayerStatusSetupData.GUEST), true);
        createUserIfNotFound("roberto.rossi@gmx.it", "Roberto", "Rossi", "password", UserStatus.GUEST, getUserRole(), getPlayerStatus(PlayerStatusSetupData.GUEST), true);
        createUserIfNotFound("julia.schmidt@gmx.de", "Julia", "Schmidt", "password", UserStatus.GUEST_PENDING, getUserRole(), getPlayerStatus(PlayerStatusSetupData.GUEST), true);
        createUserIfNotFound("tobias.wolf@hotmail.de", "Tobias", "Wolf", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
        createUserIfNotFound("jihoon.lee@gmail.com", "Ju-Hoon", "Lee", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
    }

    @Nonnull
    private PlayerStatus getPlayerStatus(@Nonnull final String name) {
        final Optional<PlayerStatus> playerStatus = playerStatusService.findByName(name);
        if (playerStatus.isPresent()) {
            return playerStatus.get();
        }
        throw new RecordNotFoundException("PlayerStatus " + name + " not found");
    }

    @Nonnull
    private Set<Role> getAdminRole() {
        return roleService.getDefaultAdminRoles();
    }

    @Nonnull
    private Set<Role> getStaffRole() {
        return roleService.getDefaultStaffRoles();
    }

    @Nonnull
    private Set<Role> getUserRole() {
        return roleService.getDefaultUserRoles();
    }

    @Transactional
    private void createUserIfNotFound(@Nonnull final String email,
                                      @Nonnull final String firstName,
                                      @Nonnull final String lastName,
                                      @Nonnull final String plainPassword,
                                      @Nonnull final UserStatus userStatus,
                                      @Nonnull final Set<Role> roles,
                                      @Nonnull final PlayerStatus playerStatus,
                                      final boolean activationStatus) {
        final Optional<User> existingUser = userService.findByEmail(email);

        if (existingUser.isEmpty()) {
            final User user = userFactory.newGuestUser(firstName, lastName, email, plainPassword, activationStatus);
            user.setRoles(roles);
            user.setUserStatus(userStatus);
            user.setPlayerStatus(playerStatus);
            userService.create(user);
        }
    }
}