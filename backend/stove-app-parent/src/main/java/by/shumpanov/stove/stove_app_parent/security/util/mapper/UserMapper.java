package by.shumpanov.stove.stove_app_parent.security.util.mapper;

import by.shumpanov.stove.stove_app_parent.security.dto.UserDto;
import by.shumpanov.stove.stove_app_parent.security.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User entity);
    List<UserDto> toDtoList(List<User> entities);
}
