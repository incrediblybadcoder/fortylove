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

    @Column(name= "show_articles")
    private boolean showArticles;

    protected UserSettings() {
    }

    public UserSettings(@Nonnull final Theme theme,
                        final boolean showArticles) {
        super(UUID.randomUUID());
        this.theme = theme;
        this.showArticles = showArticles;
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

    public boolean isShowArticles() {
        return showArticles;
    }

    public void setShowArticles(final boolean showArticles) {
        this.showArticles = showArticles;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                ", theme=" + theme +
                ", showArticles=" + showArticles +
                '}';
    }
}