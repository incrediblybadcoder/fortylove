package ch.fortylove.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "id", unique = true, nullable = false)
    private Long id = 0L;

    @Version
    @Column(name = "version")
    private int version;

    public AbstractEntity() {
    }

    public AbstractEntity(@Nonnull final Long id,
                          final int version) {
        this.id = id;
        this.version = version;
    }

    @Nonnull
    public Long getId() {
        return id;
    }

    public void setId(@Nonnull final Long id) {
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
