package ch.fortylove.configuration.devsetupdata;

import ch.fortylove.configuration.setupdata.SetupDataLoaderService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SpringComponent
@DevSetupData
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