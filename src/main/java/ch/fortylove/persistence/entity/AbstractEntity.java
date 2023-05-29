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
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private int version;

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
