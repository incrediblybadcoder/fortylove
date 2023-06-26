package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.TimeSlot;
import ch.fortylove.persistence.entity.TimeSlotEntity;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface TimeSlotMapper {

    TimeSlot convert(TimeSlotEntity timeSlotEntity);

    TimeSlotEntity convert(TimeSlot timeSlot);

}
