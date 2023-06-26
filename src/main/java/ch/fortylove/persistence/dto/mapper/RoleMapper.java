package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.RoleDTO;
import ch.fortylove.persistence.entity.RoleEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface RoleMapper {

    RoleDTO convert(RoleEntity roleEntity, @Context CycleAvoidingMappingContext context);

    RoleEntity convert(RoleDTO role, @Context CycleAvoidingMappingContext context);
}
