package ch.fortylove.service;

import ch.fortylove.SpringTest;
import ch.fortylove.testdata.factory.TestDataFactory;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

@SpringTest
abstract class ServiceTest {

    @Autowired private TestDataFactory testDataFactory;

    @Nonnull
    public TestDataFactory getTestDataFactory() {
        return testDataFactory;
    }
}
