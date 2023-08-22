package ch.fortylove.service.email;

import ch.fortylove.persistence.error.EmailNotSentException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("sendGridEmailService")
public class SendGridEmailService implements EmailServiceProvider {

    @Nonnull private final String apiKey = System.getenv("MAIL_PASSWORD");

    public void sendEmail(@Nonnull final String to,
                          @Nonnull final String subject,
                          @Nonnull final String content) {
        // Diese E-Mail Adresse muss im SendGrid Account als "Sender" hinterlegt und verifiziert sein
        Email from = new Email("fortylove.untervaz@gmail.com");
        Email toAddress = new Email(to);
        Content emailContent = new Content("text/html", content);
        Mail mail = new Mail(from, subject, toAddress, emailContent);

        SendGrid sendGrid = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());

            if (response.getStatusCode() != 202) {
                throw new EmailNotSentException("Failed to send email. Status code: " + response.getStatusCode() + ", Response: " + response.getBody());
            }

        } catch (IOException ex) {
            throw new EmailNotSentException("Error while sending the email", ex);
        }
    }
}

