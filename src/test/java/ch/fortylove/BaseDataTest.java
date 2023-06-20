package ch.fortylove;

import ch.fortylove.testsetupdata.TestSetupDataLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

@SpringTest
public class BaseDataTest {

    @Autowired TestSetupDataLoaderService testSetupDataLoaderService;

    @BeforeEach
    void initTestData() {
        testSetupDataLoaderService.initData();
    }
}
