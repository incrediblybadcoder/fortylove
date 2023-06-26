package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.TimeSlotDTO;
import ch.fortylove.persistence.entity.TimeSlotEntity;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface TimeSlotMapper {

    TimeSlotDTO convert(TimeSlotEntity timeSlotEntity);

    TimeSlotEntity convert(TimeSlotDTO timeSlot);

}
