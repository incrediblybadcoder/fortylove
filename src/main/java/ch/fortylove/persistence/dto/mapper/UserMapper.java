package ch.fortylove.persistence.dto.mapper;

import ch.fortylove.persistence.dto.User;
import ch.fortylove.persistence.entity.UserEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    User convert(UserEntity userEntity, @Context CycleAvoidingMappingContext context);

    UserEntity convert(User user, @Context CycleAvoidingMappingContext context);

    List<User> convert(List<UserEntity> userEntities, @Context CycleAvoidingMappingContext context);
}
