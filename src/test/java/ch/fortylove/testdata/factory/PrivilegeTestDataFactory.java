package ch.fortylove.testdata.factory;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.service.PrivilegeService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SpringComponent
public class PrivilegeTestDataFactory {

    @Nonnull public static final String DEFAULT_PRIVILEGE = "default_privilege";

    @Nonnull private final PrivilegeService privilegeService;

    @Autowired
    public PrivilegeTestDataFactory(@Nonnull final PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @Nonnull
    public Privilege createPrivilege(@Nonnull final String name) {
        return privilegeService.create(new Privilege(name)).getData().get();
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
