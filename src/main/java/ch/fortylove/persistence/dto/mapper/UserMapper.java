package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.UserDTO;
import ch.fortylove.persistence.entity.UserEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    UserDTO convert(UserEntity userEntity, @Context CycleAvoidingMappingContext context);

    UserEntity convert(UserDTO user, @Context CycleAvoidingMappingContext context);

    List<UserDTO> convert(List<UserEntity> userEntities, @Context CycleAvoidingMappingContext context);
}
