package ch.fortylove.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "privileges")
public class Privilege extends AbstractEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "privileges", fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    protected Privilege() {
        super();
    }

    public Privilege(@Nonnull final String name) {
        super(UUID.randomUUID());
        this.name = name;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull final String name) {
        this.name = name;
    }

    @Nonnull
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(@Nonnull final Set<Role> roles) {
        this.roles = roles;
    }
}