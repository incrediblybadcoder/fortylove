package ch.fortylove;

import ch.fortylove.devsetupdata.DevSetupDataLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

@SpringTest
public class BaseDataTest {

    @Autowired DevSetupDataLoaderService devSetupDataLoaderService;

    @BeforeEach
    void initTestData() {
        devSetupDataLoaderService.initData();
    }
}
