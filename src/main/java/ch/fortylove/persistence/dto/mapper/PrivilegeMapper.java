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

    PrivilegeDTO convert(Privilege privilege, @Context CycleAvoidingMappingContext context);

    Privilege convert(PrivilegeDTO privilegeDTO, @Context CycleAvoidingMappingContext context);

    List<PrivilegeDTO> convert(List<Privilege> privileges, @Context CycleAvoidingMappingContext context);
}
