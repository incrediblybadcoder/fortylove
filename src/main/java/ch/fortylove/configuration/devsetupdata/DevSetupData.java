package ch.fortylove.configuration.devsetupdata;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringComponent
@Profile({"develop"})
public @interface DevSetupData {
}
