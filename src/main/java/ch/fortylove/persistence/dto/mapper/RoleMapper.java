package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.RoleDTO;
import ch.fortylove.persistence.entity.Role;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface RoleMapper {

    RoleDTO convert(Role roleEntity, @Context CycleAvoidingMappingContext context);

    Role convert(RoleDTO role, @Context CycleAvoidingMappingContext context);
}
