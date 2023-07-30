package ch.fortylove.persistence.entity;

import javax.annotation.Nonnull;

public enum CourtIcon {
    ORANGE("orange", "Orange", "icons/icon-court-orange.svg"),
    GREEN("green", "Grün", "icons/icon-court-green.svg"),
    BLUE("grey", "Blau", "icons/icon-court-blue.svg"),
    GREY("blue", "Grau", "icons/icon-court-grey.svg");

    @Nonnull private final String code;
    @Nonnull private final String name;
    @Nonnull private final String resource;

    CourtIcon(@Nonnull final String code,
              @Nonnull final String name,
              @Nonnull final String resource) {
        this.code = code;
        this.name = name;
        this.resource = resource;
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
    public String getResource() {
        return resource;
    }

    @Override
    public String toString() {
        return "CourtType{" +
                "code='" + code + '\'' +
                '}';
    }
}
