package ch.fortylove.persistence.dto;

import java.util.List;
import java.util.Objects;

public class UserDTO {

    private final long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final boolean enabled;
    private final List<RoleDTO> roles;
    private final List<BookingDTO> bookings;

    public UserDTO(final long id,
                   final String firstName,
                   final String lastName,
                   final String email,
                   final String password,
                   final boolean enabled,
                   final List<RoleDTO> roles,
                   final List<BookingDTO> bookings) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
        this.bookings = bookings;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public List<BookingDTO> getBookings() {
        return bookings;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
