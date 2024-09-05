package com.appcent.todoapp.mapper;



import com.appcent.todoapp.dto.UserDto;
import com.appcent.todoapp.model.User;
import com.appcent.todoapp.request.CreateUserRequest;
import com.appcent.todoapp.request.UpdateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper USER_MAPPER= Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> userList);

    User createUser(CreateUserRequest request);

    void updateUserRequest(UpdateUserRequest request, @MappingTarget User user);

}
