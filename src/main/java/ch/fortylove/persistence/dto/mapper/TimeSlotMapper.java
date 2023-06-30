package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.TimeSlotDTO;
import ch.fortylove.persistence.entity.TimeSlot;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface TimeSlotMapper {

    TimeSlotDTO convert(TimeSlot timeSlot);

    TimeSlot convert(TimeSlotDTO timeSlotDTO);
}
