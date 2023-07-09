package ch.fortylove.persistence.entity.converter;

import ch.fortylove.persistence.entity.CourtType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CourtTypeConverter implements AttributeConverter<CourtType, String> {

    @Override
    public String convertToDatabaseColumn(@Nonnull final CourtType courtType) {
        return courtType.getCode();
    }

    @Override
    public CourtType convertToEntityAttribute(@Nonnull final String code) {
        return Stream.of(CourtType.values())
                .filter(courtType -> courtType.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
