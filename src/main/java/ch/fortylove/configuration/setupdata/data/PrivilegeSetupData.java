package ch.fortylove.configuration.setupdata.data;

import ch.fortylove.configuration.setupdata.SetupData;
import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.service.PrivilegeService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SetupData
public class PrivilegeSetupData implements ch.fortylove.configuration.setupdata.data.SetupData {

    @Nonnull public final static String MANAGEMENT_USER_CREATE = "MANAGEMENT_USER_CREATE";
    @Nonnull public final static String MANAGEMENT_USER_DELETE = "MANAGEMENT_USER_DELETE";
    @Nonnull public final static String MANAGEMENT_USER_MODIFY = "MANAGEMENT_USER_MODIFY";

    @Nonnull public final static String MANAGEMENT_COURT_CREATE = "MANAGEMENT_COURT_CREATE";
    @Nonnull public final static String MANAGEMENT_COURT_DELETE = "MANAGEMENT_COURT_DELETE";
    @Nonnull public final static String MANAGEMENT_COURT_MODIFY = "MANAGEMENT_COURT_MODIFY";

    @Nonnull private final PrivilegeService privilegeService;

    @Autowired
    public PrivilegeSetupData(@Nonnull final PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @Override
    public void createData() {
        createPrivilegeIfNotFound(MANAGEMENT_USER_CREATE);
        createPrivilegeIfNotFound(MANAGEMENT_USER_DELETE);
        createPrivilegeIfNotFound(MANAGEMENT_USER_MODIFY);

        createPrivilegeIfNotFound(MANAGEMENT_COURT_CREATE);
        createPrivilegeIfNotFound(MANAGEMENT_COURT_DELETE);
        createPrivilegeIfNotFound(MANAGEMENT_COURT_MODIFY);
    }

    @Transactional
    private void createPrivilegeIfNotFound(@Nonnull final String name) {
        final Optional<Privilege> privilege = privilegeService.findByName(name);
        if (privilege.isEmpty()) {
            privilegeService.create(new Privilege(name));
        }
    }
}