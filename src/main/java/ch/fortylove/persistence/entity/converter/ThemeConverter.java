package ch.fortylove.persistence.entity.converter;

import ch.fortylove.persistence.entity.Theme;
import jakarta.annotation.Nonnull;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ThemeConverter implements AttributeConverter<Theme, String> {

    @Override
    public String convertToDatabaseColumn(@Nonnull final Theme theme) {
        return theme.getCode();
    }

    @Override
    public Theme convertToEntityAttribute(@Nonnull final String code) {
        return Stream.of(Theme.values())
                .filter(theme -> theme.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
