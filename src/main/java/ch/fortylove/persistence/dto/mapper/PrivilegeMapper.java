package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.PrivilegeDTO;
import ch.fortylove.persistence.entity.PrivilegeEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface PrivilegeMapper {

    PrivilegeDTO convert(PrivilegeEntity privilegeEntity, @Context CycleAvoidingMappingContext context);

    PrivilegeEntity convert(PrivilegeDTO privilege, @Context CycleAvoidingMappingContext context);

    List<PrivilegeDTO> convert(List<PrivilegeEntity> privilegeEntities, @Context CycleAvoidingMappingContext context);
}
