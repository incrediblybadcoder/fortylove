package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import javax.annotation.Nonnull;
import java.util.Collection;

@Entity(name = "privileges")
public class Privilege extends AbstractEntity {

    public final static String READ_PRIVILEGE = "READ_PRIVILEGE";
    public final static String WRITE_PRIVILEGE = "WRITE_PRIVILEGE";
    public final static String CHANGE_PASSWORD_PRIVILEGE = "CHANGE_PASSWORD_PRIVILEGE";

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege() {
        super();
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull final String name) {
        this.name = name;
    }

    @Nonnull
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(@Nonnull final Collection<Role> roles) {
        this.roles = roles;
    }
}