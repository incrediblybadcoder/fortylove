package ch.fortylove.testdata.factory;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.service.RoleServiceImpl;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringComponent
public class RoleTestDataFactory {

    @Nonnull public static final String DEFAULT_ROLE = "default_role";

    @Nonnull private final RoleServiceImpl roleService;
    @Nonnull private final PrivilegeTestDataFactory privilegeTestDataFactory;

    @Autowired
    public RoleTestDataFactory(@Nonnull final RoleServiceImpl roleService,
                               @Nonnull final PrivilegeTestDataFactory privilegeTestDataFactory) {
        this.roleService = roleService;
        this.privilegeTestDataFactory = privilegeTestDataFactory;
    }

    @Nonnull
    public Role createRole(@Nonnull final String name) {
        final List<Privilege> privileges = Collections.singletonList(privilegeTestDataFactory.getDefault());
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
