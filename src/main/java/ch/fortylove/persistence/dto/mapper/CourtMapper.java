package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.entity.Court;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface CourtMapper {

    CourtDTO convert(Court court, @Context CycleAvoidingMappingContext context);

    Court convert(CourtDTO courtDTO, @Context CycleAvoidingMappingContext context);

    List<CourtDTO> convert(List<Court> courts, @Context CycleAvoidingMappingContext context);
}
