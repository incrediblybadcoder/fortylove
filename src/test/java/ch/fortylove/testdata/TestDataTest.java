package ch.fortylove.testdata;

import ch.fortylove.SpringTest;
import ch.fortylove.devsetupdata.DevSetupDataLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Adds optional test data to the database
 */
@SpringTest
public abstract class TestDataTest extends BaseDataTest {

    @Autowired DevSetupDataLoaderService devSetupDataLoaderService;

    @Override
    @BeforeEach
    void initTestData() {
        super.initTestData();
        devSetupDataLoaderService.initData();
    }
}
