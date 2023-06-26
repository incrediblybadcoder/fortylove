package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.UserDTO;
import ch.fortylove.persistence.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    UserDTO convert(User userEntity, @Context CycleAvoidingMappingContext context);

    User convert(UserDTO user, @Context CycleAvoidingMappingContext context);

    List<UserDTO> convert(List<User> userEntities, @Context CycleAvoidingMappingContext context);
}
