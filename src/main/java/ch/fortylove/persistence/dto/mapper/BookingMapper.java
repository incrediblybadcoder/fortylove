package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.entity.Booking;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface BookingMapper {

    BookingDTO convert(Booking booking, @Context CycleAvoidingMappingContext context);

    Booking convert(BookingDTO bookingDTO, @Context CycleAvoidingMappingContext context);

    List<BookingDTO> convert(List<Booking> bookings, @Context CycleAvoidingMappingContext context);
}
