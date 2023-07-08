package ch.fortylove.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import javax.annotation.Nonnull;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}
