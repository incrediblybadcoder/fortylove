package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.Role;
import ch.fortylove.persistence.entity.RoleEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface RoleMapper {

    Role convert(RoleEntity roleEntity, @Context CycleAvoidingMappingContext context);

    RoleEntity convert(Role role, @Context CycleAvoidingMappingContext context);
}
