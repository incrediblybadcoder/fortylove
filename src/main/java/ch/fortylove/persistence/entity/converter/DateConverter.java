package ch.fortylove.persistence.entity.converter;

import jakarta.annotation.Nonnull;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@Profile({"develop"})
@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<LocalDate, LocalDate> {

    @Override
    public LocalDate convertToDatabaseColumn(@Nonnull final LocalDate date) {
        return date;
    }

    @Override
    public LocalDate convertToEntityAttribute(@Nonnull final LocalDate date) {
        return date.plusDays(1);
    }
}
