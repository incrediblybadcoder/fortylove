package ch.fortylove.persistence.entity.converter;

import ch.fortylove.persistence.entity.UserStatus;
import jakarta.annotation.Nonnull;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

    @Override
    public String convertToDatabaseColumn(@Nonnull final UserStatus userStatus) {
        return userStatus.getCode();
    }

    @Override
    public UserStatus convertToEntityAttribute(@Nonnull final String code) {
        return Stream.of(UserStatus.values())
                .filter(userStatus -> userStatus.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
