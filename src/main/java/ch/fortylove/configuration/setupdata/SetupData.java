package ch.fortylove.configuration.setupdata;

import com.vaadin.flow.spring.annotation.SpringComponent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringComponent
public @interface SetupData {
}
