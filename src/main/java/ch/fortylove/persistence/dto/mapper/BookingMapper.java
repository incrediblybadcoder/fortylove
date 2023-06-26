package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.Booking;
import ch.fortylove.persistence.entity.BookingEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface BookingMapper {

    Booking convert(BookingEntity bookingEntity, @Context CycleAvoidingMappingContext context);

    BookingEntity convert(Booking booking, @Context CycleAvoidingMappingContext context);

    List<Booking> convert(List<BookingEntity> bookingEntities, @Context CycleAvoidingMappingContext context);
}
