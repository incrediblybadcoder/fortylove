package ch.fortylove.testsetupdata;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
@Profile({"h2", "develop", "local"})
public class DevSetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Nonnull private final TestSetupDataLoaderService testSetupDataLoaderService;

    private boolean alreadySetup = false;

    @Autowired
    public DevSetupDataLoader(@Nonnull final TestSetupDataLoaderService testSetupDataLoaderService) {
        this.testSetupDataLoaderService = testSetupDataLoaderService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@Nonnull final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        testSetupDataLoaderService.initData();

        alreadySetup = true;
    }
}