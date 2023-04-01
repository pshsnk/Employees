package com.innowise.employees.mappers;

import com.innowise.employees.dto.UserDTO;
import com.innowise.employees.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO toDTO(User user);
}
