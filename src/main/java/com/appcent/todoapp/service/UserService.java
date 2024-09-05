package com.appcent.todoapp.service;

import com.appcent.todoapp.dto.UserDto;
import com.appcent.todoapp.request.CreateUserRequest;
import com.appcent.todoapp.request.UpdateUserRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<UserDto> createUser(CreateUserRequest createUserRequest);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UpdateUserRequest updateUserRequest);
}
