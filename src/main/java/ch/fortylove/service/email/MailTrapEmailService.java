package ch.fortylove.service.email;

import ch.fortylove.persistence.error.EmailSendingException;
import jakarta.annotation.Nonnull;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("mailTrapEmailService")
public class MailTrapEmailService implements IEmailServiceProvider {

    @Nonnull private final JavaMailSender mailSender;

    public MailTrapEmailService(@Nonnull final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(@Nonnull final String to,
                          @Nonnull final String subject,
                          @Nonnull final String content)  {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true indicates HTML content
            System.out.println("Sending mailtrap mail to " + to);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailSendingException("Error while sending the email using MailTrap", e);
        }
    }
}
