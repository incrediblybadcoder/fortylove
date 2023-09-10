package ch.fortylove.persistence.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity(name = "unvalidatedusers")
public class UnvalidatedUser extends AbstractEntity implements HasIdentifier {

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @Email
    @NotBlank
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Column(name = "encrypted_password", length = 60)
    private String encryptedPassword;

    @NotNull
    @Column(name = "activation_code")
    private String activationCode;

    protected UnvalidatedUser() {
    }

    public UnvalidatedUser(@Nonnull final String firstName,
                           @Nonnull final String lastName,
                           @Nonnull final String email,
                           @Nonnull final String encryptedPassword) {
        super(UUID.randomUUID());
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.activationCode = UUID.randomUUID().toString();
    }

    @Nonnull
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Nonnull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Nonnull final String firstName) {
        this.firstName = firstName;
    }

    @Nonnull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@Nonnull final String lastName) {
        this.lastName = lastName;
    }

    @Nonnull
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nonnull final String email) {
        this.email = email;
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
    public String toString() {
        return "UnvalidatedUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", activationCode='" + activationCode + '\'' +
                '}';
    }

    @Nonnull
    @Override
    public String getIdentifier() {
        return getFullName();
    }
}