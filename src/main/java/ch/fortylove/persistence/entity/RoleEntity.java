package ch.fortylove.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.List;
import java.util.Objects;

@Entity(name = "roles")
public class RoleEntity extends AbstractEntity {

    public final static String ROLE_ADMIN = "ROLE_ADMIN";
    public final static String ROLE_STAFF = "ROLE_STAFF";
    public final static String ROLE_USER = "ROLE_USER";

    private String name;

    @ManyToMany(
            mappedBy = "roles",
            fetch = FetchType.EAGER
    )
    private List<UserEntity> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id")
    )
    private List<PrivilegeEntity> privileges;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(final List<UserEntity> users) {
        this.users = users;
    }

    public List<PrivilegeEntity> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(final List<PrivilegeEntity> privileges) {
        this.privileges = privileges;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RoleEntity role = (RoleEntity) o;
        return Objects.equals(name, role.name) &&
                Objects.equals(users, role.users) &&
                Objects.equals(privileges, role.privileges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, users, privileges);
    }
}