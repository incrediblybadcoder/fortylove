package ch.fortylove.persistence.entity.converter;

import ch.fortylove.persistence.entity.CourtIcon;
import jakarta.annotation.Nonnull;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class CourtIconConverter implements AttributeConverter<CourtIcon, String> {

    @Override
    public String convertToDatabaseColumn(@Nonnull final CourtIcon courtIcon) {
        return courtIcon.getCode();
    }

    @Override
    public CourtIcon convertToEntityAttribute(@Nonnull final String code) {
        return Stream.of(CourtIcon.values())
                .filter(courtIcon -> courtIcon.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
