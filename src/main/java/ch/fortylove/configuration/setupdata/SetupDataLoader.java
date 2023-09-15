package ch.fortylove.configuration.setupdata;

import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@SpringComponent
@Profile({"production"})
public class SetupDataLoader implements InitializingBean {

    @Nonnull private final SetupDataLoaderService setupDataLoaderService;

    private boolean alreadySetup = false;

    @Autowired
    public SetupDataLoader(@Nonnull final SetupDataLoaderService setupDataLoaderService) {
        this.setupDataLoaderService = setupDataLoaderService;
    }

    @Override
    @Transactional
    public void afterPropertiesSet() {
        if (alreadySetup) {
            return;
        }

        setupDataLoaderService.initData();

        alreadySetup = true;
    }
}