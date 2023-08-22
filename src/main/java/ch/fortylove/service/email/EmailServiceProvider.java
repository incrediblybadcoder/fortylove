package ch.fortylove.service.email;

import jakarta.annotation.Nonnull;

public interface EmailServiceProvider {
    void sendEmail(@Nonnull final String to, @Nonnull final  String subject, @Nonnull final  String content) throws Exception;
}
