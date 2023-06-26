package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.BookingSettings;
import ch.fortylove.persistence.entity.BookingSettingsEntity;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface BookingSettingsMapper {

    BookingSettings convert(BookingSettingsEntity bookingSettingsEntity);

    BookingSettingsEntity convert(BookingSettings bookingSettings);
}
