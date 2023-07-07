package ch.fortylove.persistence.setupdata;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

@SpringComponent
@Profile({"h2", "develop", "local", "production"})
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