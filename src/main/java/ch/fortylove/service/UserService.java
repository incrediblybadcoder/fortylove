package ch.fortylove.service;

import ch.fortylove.configuration.setupdata.data.DefaultUserSetupData;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.persistence.error.RecordNotFoundException;
import ch.fortylove.persistence.repository.UserRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Nonnull private final UserRepository userRepository;

    @Nonnull private final MailSender mailSender;

    @Autowired
    public UserService(@Nonnull final UserRepository userRepository,
                       @Nonnull final MailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
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
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@fortylove.ch");
            message.setSubject("Aktivierung Ihres fortylove Kontos");
            message.setText("http://localhost:8080/activate?code=" + user.getAuthenticationDetails().getActivationCode());
            message.setTo(user.getEmail());
            mailSender.send(message);
        }
        else {
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
    public Optional<User> findById(@Nonnull final UUID id) {
        return userRepository.findById(id);
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
}
