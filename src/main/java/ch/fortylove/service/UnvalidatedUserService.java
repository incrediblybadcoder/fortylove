package ch.fortylove.service;

import ch.fortylove.persistence.entity.UnvalidatedUser;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.error.EmailSendingException;
import ch.fortylove.persistence.repository.UnvalidatedUserRepository;
import ch.fortylove.service.email.IEmailServiceProvider;
import ch.fortylove.service.util.DatabaseResult;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UnvalidatedUserService {

    @Nonnull private final UnvalidatedUserRepository unvalidatedUserRepository;
    @Nonnull private final UserService userService;
    @Nonnull private final IEmailServiceProvider emailServiceProvider;
    @Nonnull private final String baseUrl;

    @Autowired
    public UnvalidatedUserService(@Nonnull final UnvalidatedUserRepository unvalidatedUserRepository,
                                  @Nonnull final UserService userService,
                                  @Nonnull IEmailServiceProvider emailServiceProvider) {
        this.unvalidatedUserRepository = unvalidatedUserRepository;
        this.userService = userService;
        baseUrl = System.getenv("BASE_URL");
        this.emailServiceProvider = emailServiceProvider;
    }

    @Nonnull
    public DatabaseResult<UnvalidatedUser> create(@Nonnull final UnvalidatedUser unvalidatedUser) {
        return create(unvalidatedUser, false);
    }

    @Nonnull
    public DatabaseResult<UnvalidatedUser> create(@Nonnull final UnvalidatedUser unvalidatedUser,
                                                  final boolean sendActivationMail) {
        if (unvalidatedUserRepository.findById(unvalidatedUser.getId()).isPresent()) {
            return new DatabaseResult<>("Benutzer existiert bereits: " + unvalidatedUser.getIdentifier());
        }

        if (unvalidatedUserRepository.findByEmail(unvalidatedUser.getEmail()) != null) {
            return new DatabaseResult<>("Benutzer mit folgender E-Mail existiert bereits: " + unvalidatedUser.getEmail());
        }

        // Hier, an der Stelle, wo der unvalidierte User erstellt wird, soll zentral an einer Stelle
        // der Aktivierungslink generiert und dem User mitgeteilt werden
        if (sendActivationMail) {
            final String activationLink = baseUrl + "activate?code=" + unvalidatedUser.getActivationCode();
            final String htmlContent = "Bitte klicken Sie auf den folgenden <a clicktracking=off href='" + activationLink + "'>Link</a>, um Ihr Konto zu aktivieren. "
                    + "Falls der Link auf Ihrem Gerät nicht ordnungsgemäss funktioniert, können Sie den folgenden Link kopieren und in Ihren Webbrowser einfügen: <br><br>"
                    + activationLink;


            try {
                emailServiceProvider.sendEmail(unvalidatedUser.getEmail(), "Aktivierung Ihres fortylove Kontos", htmlContent);
            } catch (EmailSendingException e) {
                return new DatabaseResult<>("Error while sending the email using " + e);
            }
        }
        return new DatabaseResult<>(unvalidatedUserRepository.save(unvalidatedUser));
    }


    /**
     * Transferiert einen unvalidierten Benutzer in einen validierten Benutzer.
     *
     * @param activationCode Der Aktivierungscode, der verwendet wird, um den spezifischen Benutzer zu finden.
     * @return {@code true} wenn der Benutzer erfolgreich aktiviert wurde, {@code false} wenn kein Benutzer mit dem gegebenen Aktivierungscode gefunden wurde.
     */
    public DatabaseResult<User> activate(@Nonnull final String activationCode) {
        final UnvalidatedUser unvalidatedUser = unvalidatedUserRepository.findByActivationCode(activationCode);
        if (unvalidatedUser != null) {
            // userService.create erstellt einen User welcher enabled ist
            final DatabaseResult<User> userDatabaseResult = userService.create(unvalidatedUser);
            if (userDatabaseResult.isSuccessful()) {
                unvalidatedUserRepository.delete(unvalidatedUser);
                return userDatabaseResult;
            } else {
                return userDatabaseResult;
            }
        } else {
            return new DatabaseResult<>("Unvalidated User mit folgenden Aktivierungscode existiert nicht: " + activationCode);
        }
    }

    @Nonnull
    public Optional<UnvalidatedUser> findByEmail(@Nonnull final String email) {
        return Optional.ofNullable(unvalidatedUserRepository.findByEmail(email));
    }

    public boolean checkIfReadyToActivate(@Nonnull final String activationCode) {
        final UnvalidatedUser unvalidatedUser = unvalidatedUserRepository.findByActivationCode(activationCode);
        return unvalidatedUser != null;
    }
}
