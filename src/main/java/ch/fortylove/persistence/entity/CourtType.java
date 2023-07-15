package ch.fortylove.persistence.entity;

import javax.annotation.Nonnull;

public enum CourtType {
    CLAY("C", "Sand", "icons/icon-court-clay.svg"),
    GRASS("G", "Grass", "icons/icon-court-grass.svg"),
    SYNTHETIC("S", "Teppich", "icons/icon-court-synthetic.svg"),
    HARD("H", "Hart", "icons/icon-court-hard.svg");

    @Nonnull private final String code;
    @Nonnull private final String material;
    @Nonnull private final String icon;

    CourtType(@Nonnull final String code,
              @Nonnull final String material,
              @Nonnull final String icon) {
        this.code = code;
        this.material = material;
        this.icon = icon;
    }

    @Nonnull
    public String getCode() {
        return code;
    }

    @Nonnull
    public String getMaterial() {
        return material;
    }

    @Nonnull
    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "CourtType{" +
                "code='" + code + '\'' +
                ", material='" + material + '\'' +
                '}';
    }
}
