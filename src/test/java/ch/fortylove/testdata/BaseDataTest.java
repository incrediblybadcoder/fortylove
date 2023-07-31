package ch.fortylove.testdata;

import ch.fortylove.SpringTest;
import ch.fortylove.configuration.setupdata.SetupDataLoaderService;
import ch.fortylove.testdata.factory.TestDataFactory;
import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Adds required application data to the database
 */
@SpringTest
public abstract class BaseDataTest {

    @Autowired private SetupDataLoaderService setupDataLoaderService;
    @Autowired private TestDataFactory testDataFactory;

    @BeforeEach
    void initTestData() {
        setupDataLoaderService.initData();
    }

    @Nonnull
    public TestDataFactory getTestDataFactory() {
        return testDataFactory;
    }
}
