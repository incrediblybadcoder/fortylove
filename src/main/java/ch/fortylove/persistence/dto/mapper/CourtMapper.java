package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.entity.CourtEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface CourtMapper {

    CourtDTO convert(CourtEntity courtEntity, @Context CycleAvoidingMappingContext context);

    CourtEntity convert(CourtDTO court, @Context CycleAvoidingMappingContext context);

    List<CourtDTO> convert(List<CourtEntity> courtEntities, @Context CycleAvoidingMappingContext context);
}
