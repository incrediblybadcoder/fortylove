package ch.fortylove.service.email;

import jakarta.annotation.Nonnull;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("mailTrapEmailService")
public class MailTrapEmailService implements EmailServiceProvider {

    @Nonnull private final JavaMailSender mailSender;

    public MailTrapEmailService(@Nonnull final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(@Nonnull final String to,
                          @Nonnull final String subject,
                          @Nonnull final String content) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true); // true indicates HTML content
        mailSender.send(mimeMessage);
    }
}
