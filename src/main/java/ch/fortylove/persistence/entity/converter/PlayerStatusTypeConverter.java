package ch.fortylove.persistence.entity.converter;

import ch.fortylove.persistence.entity.PlayerStatusType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class PlayerStatusTypeConverter implements AttributeConverter<PlayerStatusType, String> {

    @Override
    public String convertToDatabaseColumn(@Nonnull final PlayerStatusType playerStatusType) {
        return playerStatusType.getCode();
    }

    @Override
    public PlayerStatusType convertToEntityAttribute(@Nonnull final String code) {
        return Stream.of(PlayerStatusType.values())
                .filter(playerStatusType -> playerStatusType.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
