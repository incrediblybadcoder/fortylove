package ch.fortylove.testdata.factory;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.service.RoleService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

@SpringComponent
public class RoleTestDataFactory {

    @Nonnull public static final String DEFAULT_ROLE = "default_role";

    @Nonnull private final RoleService roleService;
    @Nonnull private final PrivilegeTestDataFactory privilegeTestDataFactory;

    @Autowired
    public RoleTestDataFactory(@Nonnull final RoleService roleService,
                               @Nonnull final PrivilegeTestDataFactory privilegeTestDataFactory) {
        this.roleService = roleService;
        this.privilegeTestDataFactory = privilegeTestDataFactory;
    }

    @Nonnull
    public Role createRole(@Nonnull final String name) {
        final Set<Privilege> privileges = Set.of(privilegeTestDataFactory.getDefault());
        return roleService.create(new Role(name, privileges));
    }

    @Nonnull
    public Role getDefault() {
        final Optional<Role> defaultRole = roleService.findByName(DEFAULT_ROLE);
        if (defaultRole.isEmpty()) {
            return createRole(DEFAULT_ROLE);
        }

        return defaultRole.get();
    }
}
