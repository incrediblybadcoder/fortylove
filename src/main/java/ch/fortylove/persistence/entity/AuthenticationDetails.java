package ch.fortylove.persistence.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity(name ="authenticationdetails")
public class AuthenticationDetails extends AbstractEntity {

    @NotBlank
    @Column(name = "encrypted_password", length = 60)
    private String encryptedPassword;

    @NotNull
    @Column(name = "activation_code")
    private String activationCode;

    @NotNull
    @OneToOne(mappedBy = "authenticationDetails")
    private User user;

    protected AuthenticationDetails() {
    }

    public AuthenticationDetails(@Nonnull final String encryptedPassword,
                                 @Nonnull final String activationCode) {
        super(UUID.randomUUID());
        this.encryptedPassword = encryptedPassword;
        this.activationCode = activationCode;
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

    @Nonnull
    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(@Nonnull final String activationCode) {
        this.activationCode = activationCode;
    }

    @Override
    @Nonnull
    public String toString() {
        return "UserAuthenticationDetails{" +
                ", user=" + user +
                '}';
    }
}