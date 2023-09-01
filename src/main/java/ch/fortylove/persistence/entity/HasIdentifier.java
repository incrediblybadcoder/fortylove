package ch.fortylove.persistence.entity;

import jakarta.annotation.Nonnull;

public interface HasIdentifier {

    @Nonnull String getIdentifier();
}
