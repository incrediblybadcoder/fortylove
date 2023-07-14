package ch.fortylove.testdata.factory;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.service.PrivilegeServiceImpl;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.Optional;

@SpringComponent
public class PrivilegeTestDataFactory {

    @Nonnull public static final String DEFAULT_PRIVILEGE = "default_privilege";

    @Nonnull private final PrivilegeServiceImpl privilegeService;

    @Autowired
    public PrivilegeTestDataFactory(@Nonnull final PrivilegeServiceImpl privilegeService) {
        this.privilegeService = privilegeService;
    }

    @Nonnull
    public Privilege createPrivilege(@Nonnull final String name) {
        return privilegeService.create(new Privilege(name));
    }

    @Nonnull
    public Privilege getDefault() {
        final Optional<Privilege> defaultPrivilege = privilegeService.findByName(DEFAULT_PRIVILEGE);
        if (defaultPrivilege.isEmpty()) {
            return createPrivilege(DEFAULT_PRIVILEGE);
        }

        return defaultPrivilege.get();
    }
}
