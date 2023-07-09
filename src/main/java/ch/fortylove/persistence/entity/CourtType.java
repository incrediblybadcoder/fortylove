package ch.fortylove.persistence.entity;

import javax.annotation.Nonnull;

public enum CourtType {
    CLAY("C", "Sand"),
    GRASS("G", "Grass"),
    SYNTHETIC("S", "Teppich"),
    HARD("H", "Hart");

    @Nonnull private final String code;
    @Nonnull private final String material;

    CourtType(@Nonnull final String code,
              @Nonnull final String material) {
        this.code = code;
        this.material = material;
    }

    @Nonnull
    public String getCode() {
        return code;
    }

    @Nonnull
    public String getMaterial() {
        return material;
    }
}
