package ch.fortylove.persistence.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity(name = "usersettings")
public class UserSettings extends AbstractEntity {

    @NotNull
    @OneToOne(mappedBy = "userSettings")
    private User user;

    @NotNull
    @Column(name = "theme")
    private Theme theme;

    protected UserSettings() {
    }

    public UserSettings(@Nonnull final Theme theme) {
        super(UUID.randomUUID());
        this.theme = theme;
    }

    @Nonnull
    public User getUser() {
        return user;
    }

    public void setUser(@Nonnull final User user) {
        this.user = user;
    }

    @Nonnull
    public Theme getTheme() {
        return theme;
    }

    public void setTheme(@Nonnull final Theme theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "theme=" + theme +
                '}';
    }
}