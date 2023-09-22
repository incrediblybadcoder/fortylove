package ch.fortylove.service.email;

import ch.fortylove.persistence.error.EmailSendingException;
import jakarta.annotation.Nonnull;

public interface IEmailServiceProvider {

    void sendEmail(@Nonnull final String to, @Nonnull final String subject, @Nonnull final String content) throws EmailSendingException;
}
