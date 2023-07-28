package ch.fortylove.persistence.entity;

import javax.annotation.Nonnull;

public enum CourtIcon {
    ORANGE("orange", "icons/icon-court-orange.svg"),
    GREEN("green", "icons/icon-court-green.svg"),
    BLUE("grey", "icons/icon-court-blue.svg"),
    GREY("blue", "icons/icon-court-grey.svg");

    @Nonnull private final String code;
    @Nonnull private final String resource;

    CourtIcon(@Nonnull final String code,
              @Nonnull final String resource) {
        this.code = code;
        this.resource = resource;
    }

    @Nonnull
    public String getCode() {
        return code;
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
