package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.entity.BookingEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface BookingMapper {

    BookingDTO convert(BookingEntity bookingEntity, @Context CycleAvoidingMappingContext context);

    BookingEntity convert(BookingDTO booking, @Context CycleAvoidingMappingContext context);

    List<BookingDTO> convert(List<BookingEntity> bookingEntities, @Context CycleAvoidingMappingContext context);
}
