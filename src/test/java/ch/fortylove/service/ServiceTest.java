package ch.fortylove.service;

import ch.fortylove.SpringTest;
import ch.fortylove.testdata.factory.TestDataFactory;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

@SpringTest
abstract class ServiceTest {

    @MockBean JavaMailSender javaMailSender;

    @Autowired private TestDataFactory testDataFactory;

    @Nonnull
    public TestDataFactory getTestDataFactory() {
        return testDataFactory;
    }
}
