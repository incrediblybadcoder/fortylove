package ch.fortylove.configuration.devsetupdata;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.annotation.Nonnull;

/**
 * Condition for configuration profiles for which a component should be loaded
 */
public class DevSetupDataLoaderCondition implements Condition {
    @Override
    public boolean matches(@Nonnull final ConditionContext context,
                           @Nonnull final AnnotatedTypeMetadata metadata) {
        final Environment environment = context.getEnvironment();
        return !environment.matchesProfiles("production", "test");
    }
}
