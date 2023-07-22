package ch.fortylove.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @NotNull
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Version
    @Column(name = "version")
    private int version;

    protected AbstractEntity() {
    }

    protected AbstractEntity(@Nonnull final UUID id) {
        this.id = id;
    }

    @Nonnull
    public UUID getId() {
        return id;
    }

    public void setId(@Nonnull final UUID id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AbstractEntity abstractEntity = (AbstractEntity) o;
        return Objects.equals(getId(), abstractEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
