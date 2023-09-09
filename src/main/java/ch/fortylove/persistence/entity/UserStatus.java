package ch.fortylove.persistence.entity;

import jakarta.annotation.Nonnull;

import java.util.List;

public enum UserStatus implements HasIdentifier {

    GUEST("guest", "Gast"),
    GUEST_PENDING("guestpending", "Gast (Anfrage)"),
    MEMBER("member", "Mitglied");

    @Nonnull private final String code;
    @Nonnull private final String name;

    UserStatus(@Nonnull final String code,
               @Nonnull final String name) {
        this.code = code;
        this.name = name;
    }

    @Nonnull
    public String getCode() {
        return code;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public static List<UserStatus> getManuallyManageableUserStatus() {
        return List.of(GUEST, MEMBER);
    }

    @Override
    @Nonnull
    public String toString() {
        return "UserStatus{" +
                ", name='" + name + '\'' +
                '}';
    }

    @Nonnull
    @Override
    public String getIdentifier() {
        return name;
    }
}
