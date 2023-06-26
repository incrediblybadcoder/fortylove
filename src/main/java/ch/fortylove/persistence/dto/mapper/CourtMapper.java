package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.Court;
import ch.fortylove.persistence.entity.CourtEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface CourtMapper {

    Court convert(CourtEntity courtEntity, @Context CycleAvoidingMappingContext context);

    CourtEntity convert(Court court, @Context CycleAvoidingMappingContext context);

    List<Court> convert(List<CourtEntity> courtEntities, @Context CycleAvoidingMappingContext context);
}
