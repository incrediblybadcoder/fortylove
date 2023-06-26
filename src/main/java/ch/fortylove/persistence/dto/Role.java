package ch.fortylove.persistence.dto;

import java.util.List;
import java.util.Objects;

public class Role {

    private final long id;
    private final String name;
    private final List<User> users;
    private final List<Privilege> privileges;

    public Role(final long id,
                final String name,
                final List<User> users,
                final List<Privilege> privileges) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.privileges = privileges;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Role role = (Role) o;
        return id == role.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}