package ch.fortylove.presentation.views.management.usermanagement;

import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import jakarta.annotation.Nonnull;

import java.util.Set;

public class UserFilter {

    private GridListDataView<User> dataView;
    private String firstName;
    private String lastName;
    private String email;
    private String playerStatus;
    private String roles;

    public void setDataView(@Nonnull final GridListDataView<User> dataView) {
        this.dataView = dataView;
        this.dataView.addFilter(this::filter);
    }

    public void setFirstName(@Nonnull final String firstName) {
        this.firstName = firstName;
        this.dataView.refreshAll();
    }

    public void setLastName(@Nonnull final String lastName) {
        this.lastName = lastName;
        this.dataView.refreshAll();
    }

    public void setEmail(@Nonnull final String email) {
        this.email = email;
        this.dataView.refreshAll();
    }

    public void setPlayerStatus(@Nonnull final String playerStatus) {
        this.playerStatus = playerStatus;
        this.dataView.refreshAll();
    }

    public void setRoles(@Nonnull final String roles) {
        this.roles = roles;
        this.dataView.refreshAll();
    }

    public boolean filter(@Nonnull final User user) {
        boolean matchesFirstName = matches(user.getFirstName(), firstName);
        boolean matchesLastName = matches(user.getLastName(), lastName);
        boolean matchesEmail = matches(user.getEmail(), email);
        boolean matchesPlayerStatus = matches(user.getPlayerStatus().getName(), playerStatus);
        boolean matchesRole = matches(getRolesString(user.getRoles()), roles);

        return matchesFirstName && matchesLastName && matchesEmail && matchesPlayerStatus & matchesRole;
    }

    private boolean matches(@Nonnull final String value,
                            @Nonnull final String searchTerm) {
        return searchTerm == null ||
                searchTerm.isEmpty() ||
                value.toLowerCase().contains(searchTerm.toLowerCase());
    }

    @Nonnull
    private String getRolesString(@Nonnull final Set<Role> roles) {
        final StringBuilder roleString = new StringBuilder();
        roles.forEach(role -> roleString.append(role.getName()));
        return roleString.toString();
    }
}
