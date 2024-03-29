package ch.fortylove.persistence.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name ="authenticationdetails")
public class AuthenticationDetails extends AbstractEntity {

    @NotBlank
    @Column(name = "encrypted_password", length = 60)
    private String encryptedPassword;

    @NotNull
    @OneToOne(mappedBy = "authenticationDetails")
    private User user;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "token_expire_date")
    private LocalDateTime tokenExpiryDate;


    protected AuthenticationDetails() {
    }

    public AuthenticationDetails(@Nonnull final String encryptedPassword) {
        super(UUID.randomUUID());
        this.encryptedPassword = encryptedPassword;
    }

    @Nonnull
    public User getUser() {
        return user;
    }

    public void setUser(@Nonnull final User user) {
        this.user = user;
    }

    @Nonnull
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(@Nonnull final String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }


    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(final String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getTokenExpiryDate() {
        return tokenExpiryDate;
    }

    public void setTokenExpiryDate(final LocalDateTime tokenExpiryDate) {
        this.tokenExpiryDate = tokenExpiryDate;
    }

    @Override
    @Nonnull
    public String toString() {
        return "UserAuthenticationDetails{" +
                ", user=" + user +
                '}';
    }
}