package ch.fortylove.persistence.entity;

import com.vaadin.flow.theme.lumo.Lumo;
import jakarta.annotation.Nonnull;

public enum Theme implements HasIdentifier {

    LIGHT("light", Lumo.LIGHT, "Hell"),
    DARK("dark", Lumo.DARK, "Dunkel");

    @Nonnull private final String code;
    @Nonnull private final String lumoCode;
    @Nonnull private final String name;

    Theme(@Nonnull final String code,
          @Nonnull final String lumoCode,
          @Nonnull final String name) {
        this.code = code;
        this.lumoCode = lumoCode;
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
    public String getLumoCode() {
        return lumoCode;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "code='" + code + '\'' +
                ", lumoCode='" + lumoCode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Nonnull
    @Override
    public String getIdentifier() {
        return name;
    }
}
