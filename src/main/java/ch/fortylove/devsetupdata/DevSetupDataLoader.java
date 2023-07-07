package ch.fortylove.devsetupdata;

import ch.fortylove.persistence.setupdata.SetupDataLoaderService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

@SpringComponent
@Profile({"h2", "develop", "local"})
public class DevSetupDataLoader implements InitializingBean {

    @Nonnull private final SetupDataLoaderService setupDataLoaderService;
    @Nonnull private final DevSetupDataLoaderService devSetupDataLoaderService;

    private boolean alreadySetup = false;

    @Autowired
    public DevSetupDataLoader(@Nonnull final SetupDataLoaderService setupDataLoaderService,
                              @Nonnull final DevSetupDataLoaderService devSetupDataLoaderService) {
        this.setupDataLoaderService = setupDataLoaderService;
        this.devSetupDataLoaderService = devSetupDataLoaderService;
    }

    @Override
    @Transactional
    public void afterPropertiesSet() {
        if (alreadySetup) {
            return;
        }

        setupDataLoaderService.initData();
        devSetupDataLoaderService.initData();

        alreadySetup = true;
    }
}