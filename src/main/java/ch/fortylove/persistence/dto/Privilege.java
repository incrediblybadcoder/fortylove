package ch.fortylove.persistence.dto;

import java.util.List;
import java.util.Objects;

public class Privilege {

    private final long id;
    private final String name;
    private final List<Role> roles;

    public Privilege(final long id,
                     final String name,
                     final List<Role> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Privilege that = (Privilege) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}