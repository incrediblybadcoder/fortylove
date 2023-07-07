package ch.fortylove;

import ch.fortylove.persistence.setupdata.SetupDataLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Adds required application data to the database
 */
@SpringTest
public abstract class BaseDataTest {

    @Autowired SetupDataLoaderService setupDataLoaderService;

    @BeforeEach
    void initTestData() {
        setupDataLoaderService.initData();
    }
}
