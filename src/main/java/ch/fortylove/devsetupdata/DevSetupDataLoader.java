package ch.fortylove.devsetupdata;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

@SpringComponent
@Profile({"h2", "develop", "local"})
public class DevSetupDataLoader implements InitializingBean {

    @Nonnull private final DevSetupDataLoaderService devSetupDataLoaderService;

    private boolean alreadySetup = false;

    @Autowired
    public DevSetupDataLoader(@Nonnull final DevSetupDataLoaderService devSetupDataLoaderService) {
        this.devSetupDataLoaderService = devSetupDataLoaderService;
    }

    @Override
    @Transactional
    public void afterPropertiesSet() throws Exception {
        if (alreadySetup) {
            return;
        }

        devSetupDataLoaderService.initData();

        alreadySetup = true;
    }
}