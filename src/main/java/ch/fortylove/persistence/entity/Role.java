package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.Collection;

@Entity(name = "roles")
public class Role extends AbstractEntity {

    public final static String ROLE_ADMIN = "ROLE_ADMIN";
    public final static String ROLE_STAFF = "ROLE_STAFF";
    public final static String ROLE_USER = "ROLE_USER";

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    public Role() {
        super();
    }

    public Role(final String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(final Collection<User> users) {
        this.users = users;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(final Collection<Privilege> privileges) {
        this.privileges = privileges;
    }
}