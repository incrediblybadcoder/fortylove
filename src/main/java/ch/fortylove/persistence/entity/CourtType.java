package ch.fortylove.persistence.entity;

import jakarta.annotation.Nonnull;

public enum CourtType implements HasIdentifier {

    CLAY("clay", "Sand"),
    GRASS("grass", "Grass"),
    SYNTHETIC("synthetic", "Teppich"),
    HARD("hard", "Hart"),
    REDCOURT("redcourt", "RedCourt");

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
                ", material='" + material + '\'' +
                '}';
    }

    @Nonnull
    @Override
    public String getIdentifier() {
        return material;
    }
}
