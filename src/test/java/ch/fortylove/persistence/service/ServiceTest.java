package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.testdata.factory.TestDataFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@SpringTest
public abstract class ServiceTest {

    @Autowired private TestDataFactory testDataFactory;

    @Nonnull
    public TestDataFactory getTestDataFactory() {
        return testDataFactory;
    }
}
