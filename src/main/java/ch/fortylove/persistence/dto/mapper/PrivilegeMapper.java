package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.PrivilegeDTO;
import ch.fortylove.persistence.entity.Privilege;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface PrivilegeMapper {

    PrivilegeDTO convert(Privilege privilegeEntity, @Context CycleAvoidingMappingContext context);

    Privilege convert(PrivilegeDTO privilege, @Context CycleAvoidingMappingContext context);

    List<PrivilegeDTO> convert(List<Privilege> privilegeEntities, @Context CycleAvoidingMappingContext context);
}
