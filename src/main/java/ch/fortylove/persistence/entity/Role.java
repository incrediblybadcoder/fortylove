package ch.fortylove.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "roles")
public class Role extends AbstractEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Set<Privilege> privileges = new HashSet<>();

    protected Role() {
        super();
    }

    public Role(@Nonnull final String name,
                @Nonnull final Set<Privilege> privileges) {
        super(UUID.randomUUID());
        this.name = name;
        this.privileges = privileges;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull final String name) {
        this.name = name;
    }

    @Nonnull
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(@Nonnull final Set<User> users) {
        this.users = users;
    }

    @Nonnull
    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(@Nonnull final Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}