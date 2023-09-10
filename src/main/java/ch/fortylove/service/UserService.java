package ch.fortylove.service;

import ch.fortylove.configuration.setupdata.data.DefaultUserSetupData;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.UserStatus;
import ch.fortylove.persistence.repository.UserRepository;
import ch.fortylove.service.email.EmailServiceProvider;
import ch.fortylove.service.util.DatabaseResult;
import ch.fortylove.util.DateTimeUtil;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Nonnull private final UserRepository userRepository;
    @Nonnull private final PlayerStatusService playerStatusService;
    @Nonnull private final DateTimeUtil dateTimeUtil;

    @Nonnull private final EmailServiceProvider emailServiceProvider;
    @Nonnull private final String baseUrl;

    @Autowired
    public UserService(@Nonnull final UserRepository userRepository,
                       @Nonnull final PlayerStatusService playerStatusService,
                       @Nonnull final DateTimeUtil dateTimeUtil,
                       @Value("${email.service}") String emailProvider,
                       @Nonnull final ApplicationContext context) {
        this.userRepository = userRepository;
        this.playerStatusService = playerStatusService;
        this.dateTimeUtil = dateTimeUtil;
        baseUrl = System.getenv("BASE_URL");
        emailServiceProvider = context.getBean(emailProvider, EmailServiceProvider.class);
    }

    @Nonnull
    public DatabaseResult<User> create(@Nonnull final User user) {
        return this.create(user, false);
    }

    @Nonnull
    public DatabaseResult<User> create(@Nonnull final User user,
                                       boolean sendActivationMail) {
        if (userRepository.findById(user.getId()).isPresent()) {
            return new DatabaseResult<>("Benutzer existiert bereits: " + user.getIdentifier());
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            return new DatabaseResult<>("Benutzer mit folgender E-Mail existiert bereits: " + user.getEmail());
        }

        // Hier, an der Stelle, wo der User erstellt wird, soll zentral an einer Stelle
        // der Aktivierungslink generiert und dem User mitgeteilt werden
        if (sendActivationMail) {
            final String activationLink = baseUrl + "activate?code=" + user.getAuthenticationDetails().getActivationCode();
            final String htmlContent = "Bitte klicken Sie auf den folgenden <a clicktracking=off href='" + activationLink + "'>Link</a>, um Ihr Konto zu aktivieren.";

            try {
                emailServiceProvider.sendEmail(user.getEmail(), "Aktivierung Ihres fortylove Kontos", htmlContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new DatabaseResult<>(userRepository.save(user));
    }

    @Nonnull
    public DatabaseResult<User> update(@Nonnull final User user) {
        if (userRepository.findById(user.getId()).isEmpty()) {
            return new DatabaseResult<>("Benutzer existiert nicht: " + user.getIdentifier());
        }
        return new DatabaseResult<>(userRepository.save(user));
    }

    @Nonnull
    public Optional<User> findByEmail(@Nonnull final String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Nonnull
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Nonnull
    public DatabaseResult<UUID> delete(@Nonnull final UUID id) {
        final Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return new DatabaseResult<>("Benutzer existiert nicht: " + id);
        }

        final User user = userOptional.get();

        if (user.getOwnerBookings().size() != 0 ||
                user.getOpponentBookings().size() != 0) {
            return new DatabaseResult<>("Benutzer kann nicht gelöscht werden, da er noch Buchungen hat");
        }

        userRepository.delete(user);

        return new DatabaseResult<>(id);
    }

    @Nonnull
    public List<User> getPossibleBookingOpponents(@Nonnull final User currentUser) {
        final List<User> users = userRepository.findAllEnabledWithAvailableBookingsPerDay();

        // Sich selbst und den Develop User soll man nicht als Gegner auswählen können
        users.remove(currentUser);
        return removeDevelopUser(users);
    }

    @Nonnull
    public List<User> getAllVisibleUsers() {
        final List<User> allUsers = this.findAll();

        // Der Develop User soll für den Club-Admin nicht dargestellt werden
        return removeDevelopUser(allUsers);
    }

    @Nonnull
    private List<User> removeDevelopUser(@Nonnull final List<User> userList) {
        return userList.stream()
                .filter(user -> !user.getEmail().equalsIgnoreCase(DefaultUserSetupData.DEVELOP_USER))
                .toList();
    }

    /**
     * Aktiviert einen Benutzer anhand eines gegebenen Aktivierungscodes.
     *
     * @param activationCode Der Aktivierungscode, der verwendet wird, um den spezifischen Benutzer zu finden.
     * @return {@code true} wenn der Benutzer erfolgreich aktiviert wurde, {@code false} wenn kein Benutzer mit dem gegebenen Aktivierungscode gefunden wurde.
     */
    public boolean activate(@Nonnull final String activationCode) {
        final User user = userRepository.findByActivationCode(activationCode);
        if (user != null) {
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Überprüft ob ein Benutzer aktiviert ist anhand eines gegebenen Aktivierungscodes.
     *
     * @param activationCode Der Aktivierungscode, der verwendet wird, um den spezifischen Benutzer zu finden.
     * @return {@code true} wenn der Benutzer bereits aktiv ist, {@code false} wenn der Benutzer nicht aktiv ist.
     */
    public boolean checkIfActive(@Nonnull final String activationCode) {
        final User user = userRepository.findByActivationCode(activationCode);
        if (user != null) {
            return user.isEnabled();
        } else {
            return false;
        }
    }

    public boolean generateAndSaveResetToken(@Nonnull final String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        String resetToken = UUID.randomUUID().toString();
        LocalDateTime tokenExpiryDate = LocalDateTime.now().plusHours(1); // Token soll nur eine Stunde gültig sein

        user.getAuthenticationDetails().setResetToken(resetToken);
        user.getAuthenticationDetails().setTokenExpiryDate(tokenExpiryDate);
        userRepository.save(user);


        String resetLink = baseUrl + "resetpassword?token=" + user.getAuthenticationDetails().getResetToken();
        String htmlContent = "Bitte klicken Sie auf den folgenden <a clicktracking=off href='" + resetLink + "'>Link</a>, um Ihr Passwort zu ändern.";

        try {
            emailServiceProvider.sendEmail(user.getEmail(), "Passwort zurückrücksetzen", htmlContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userRepository.save(user);
        return true;

    }

    public boolean resetPasswordUsingToken(@Nonnull final String token,
                                           @Nonnull final String newPasswordEncrypted) {
        final User user = userRepository.findByResetToken(token);

        if (user != null && isTokenValid(user.getAuthenticationDetails().getTokenExpiryDate())) {
            user.getAuthenticationDetails().setEncryptedPassword(newPasswordEncrypted);
            // Token und Expiry Date sollen nach dem erfolgreichen Zurücksetzen des Passworts gelöscht werden
            // damit der Token nicht mehrfach verwendet werden kann
            user.getAuthenticationDetails().setResetToken(null);
            user.getAuthenticationDetails().setTokenExpiryDate(null);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    private boolean isTokenValid(@Nonnull final LocalDateTime tokenExpiryDate) {
        return tokenExpiryDate.isAfter(dateTimeUtil.todayNow());
    }

    @Nonnull
    public DatabaseResult<User> changeUserStatusToMember(@Nonnull final User user,
                                                         @Nonnull final PlayerStatus playerStatus) {
        user.setUserStatus(UserStatus.MEMBER);
        user.setPlayerStatus(playerStatus);
        return update(user);
    }

    @Nonnull
    public DatabaseResult<User> changeUserStatusToGuest(@Nonnull final User user) {
        user.setUserStatus(UserStatus.GUEST);
        user.setPlayerStatus(playerStatusService.getDefaultGuestPlayerStatus());
        return update(user);
    }
}
