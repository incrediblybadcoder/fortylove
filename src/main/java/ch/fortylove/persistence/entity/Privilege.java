package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "privileges")
public class Privilege extends AbstractEntity {

    public final static String READ_PRIVILEGE = "READ_PRIVILEGE";
    public final static String WRITE_PRIVILEGE = "WRITE_PRIVILEGE";
    public final static String CHANGE_PASSWORD_PRIVILEGE = "CHANGE_PASSWORD_PRIVILEGE";

    private String name;

    @ManyToMany(
            mappedBy = "privileges",
            fetch = FetchType.EAGER
    )
    private List<Role> roles;

    public Privilege() {
        super();
        roles = new ArrayList<>();
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull final String name) {
        this.name = name;
    }

    @Nonnull
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(@Nonnull final List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Privilege privilege = (Privilege) o;
        return Objects.equals(name, privilege.name) &&
                Objects.equals(roles, privilege.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, roles);
    }
}