package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.BookingSettingsDTO;
import ch.fortylove.persistence.entity.BookingSettings;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface BookingSettingsMapper {

    BookingSettingsDTO convert(BookingSettings bookingSettings);

    BookingSettings convert(BookingSettingsDTO bookingSettingsDTO);
}
