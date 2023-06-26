package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.BookingSettingsDTO;
import ch.fortylove.persistence.entity.BookingSettingsEntity;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface BookingSettingsMapper {

    BookingSettingsDTO convert(BookingSettingsEntity bookingSettingsEntity);

    BookingSettingsEntity convert(BookingSettingsDTO bookingSettings);
}
