package ch.fortylove.persistence.dto;

import java.util.List;
import java.util.Objects;

public class RoleDTO {

    private final long id;
    private final String name;
    private final List<UserDTO> users;
    private final List<PrivilegeDTO> privileges;

    public RoleDTO(final long id,
                   final String name,
                   final List<UserDTO> users,
                   final List<PrivilegeDTO> privileges) {
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

    public List<UserDTO> getUsers() {
        return users;
    }

    public List<PrivilegeDTO> getPrivileges() {
        return privileges;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RoleDTO roleDTO = (RoleDTO) o;
        return id == roleDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}