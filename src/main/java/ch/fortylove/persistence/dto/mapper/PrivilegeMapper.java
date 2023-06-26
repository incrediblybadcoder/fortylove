package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.Privilege;
import ch.fortylove.persistence.entity.PrivilegeEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface PrivilegeMapper {

    Privilege convert(PrivilegeEntity privilegeEntity, @Context CycleAvoidingMappingContext context);

    PrivilegeEntity convert(Privilege privilege, @Context CycleAvoidingMappingContext context);

    List<Privilege> convert(List<PrivilegeEntity> privilegeEntities, @Context CycleAvoidingMappingContext context);
}
