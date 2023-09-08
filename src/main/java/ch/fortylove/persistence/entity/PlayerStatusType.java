package ch.fortylove.persistence.entity;

import jakarta.annotation.Nonnull;

public enum PlayerStatusType implements HasIdentifier{
    MEMBER("member", "Mitglied"),
    GUEST("guest", "Gast");

    @Nonnull private final String code;
    @Nonnull private final String name;

    PlayerStatusType(@Nonnull final String code,
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

    @Override
    public String toString() {
        return "PlayerStatusType{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Nonnull
    @Override
    public String getIdentifier() {
        return name;
    }
}
