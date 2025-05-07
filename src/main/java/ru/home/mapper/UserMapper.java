package ru.home.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.home.dto.UserDto;
import ru.home.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto userDto);

    UserDto toDto(User user);
}