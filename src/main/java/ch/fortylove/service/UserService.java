package ch.fortylove.service;

import ch.fortylove.configuration.setupdata.data.DefaultUserSetupData;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.persistence.error.RecordNotFoundException;
import ch.fortylove.persistence.repository.UserRepository;
import ch.fortylove.service.email.EmailServiceProvider;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Nonnull private final UserRepository userRepository;
    @Nonnull private final String baseUrl;
    @Nonnull private final EmailServiceProvider emailServiceProvider;

    @Autowired
    public UserService(@Nonnull final UserRepository userRepository,
                       @Value("${email.service}") String emailProvider,
                       @Nonnull final ApplicationContext context) {
        this.userRepository = userRepository;
        baseUrl = System.getenv("BASE_URL");
        emailServiceProvider = context.getBean(emailProvider, EmailServiceProvider.class);
    }

    @Nonnull
    public User create(@Nonnull final User user) {
        return this.create(user, false);
    }

    @Nonnull
    public User create(@Nonnull final User user, boolean sendActivationMail) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new DuplicateRecordException(user);
        }
        // Hier, an der Stelle, wo der User erstellt wird, soll zentral an einer Stelle
        // der Aktivierungslink generiert und dem User mitgeteilt werden
        if (sendActivationMail) {
            String activationLink = baseUrl + "activate?code=" + user.getAuthenticationDetails().getActivationCode();
            String htmlContent = "Bitte klicken Sie auf den folgenden <a clicktracking=off href='" + activationLink + "'>Link</a>, um Ihr Konto zu aktivieren.";

            try {
                emailServiceProvider.sendEmail(user.getEmail(), "Aktivierung Ihres fortylove Kontos", htmlContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("http://localhost:8080/activate?code=" + user.getAuthenticationDetails().getActivationCode());
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
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(@Nonnull final UUID id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        userRepository.delete(user);
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
    private List<User> removeDevelopUser(List<User> userList) {
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
    public boolean activate(String activationCode) {
        User user = userRepository.findByActivationCode(activationCode);
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
    public boolean checkIfActive(String activationCode) {
        User user = userRepository.findByActivationCode(activationCode);
        if (user != null) {
            return user.isEnabled();
        } else {
            return false;
        }
    }
}
