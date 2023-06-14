package com.example.mapper;

import com.example.dto.LoginDto;
import com.example.dto.UserCreateEditDto;
import com.example.dto.UserReadDto;
import com.example.entity.User;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserReadDto toUserDto(User user);
    User toUser(LoginDto userDto);
    User toUser(UserCreateEditDto userDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromDto(UserCreateEditDto userDto, @MappingTarget User user);
}
