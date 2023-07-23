package ch.fortylove.persistence.entity;

import javax.annotation.Nonnull;

public enum CourtType {
    CLAY("clay", "Sand"),
    GRASS("grass", "Grass"),
    SYNTHETIC("synthetic", "Teppich"),
    HARD("hard", "Hart");

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

    @Override
    public String toString() {
        return "CourtType{" +
                "code='" + code + '\'' +
                ", material='" + material + '\'' +
                '}';
    }
}
