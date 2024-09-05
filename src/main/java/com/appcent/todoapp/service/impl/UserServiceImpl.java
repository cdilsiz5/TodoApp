package com.appcent.todoapp.service.impl;

import com.appcent.todoapp.dto.UserDto;
import com.appcent.todoapp.exception.UserNotFoundException;
import com.appcent.todoapp.model.Role;
import com.appcent.todoapp.model.User;
import com.appcent.todoapp.repository.RoleRepository;
import com.appcent.todoapp.repository.UserRepository;
import com.appcent.todoapp.request.CreateUserRequest;
import com.appcent.todoapp.request.UpdateUserRequest;
import com.appcent.todoapp.security.UserDetailsImpl;
import com.appcent.todoapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.appcent.todoapp.constants.Constant.USER_NOT_FOUND;
import static com.appcent.todoapp.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<UserDto> createUser(CreateUserRequest createUserRequest) {
        log.info("Starting user creation process for email: {}", createUserRequest.getEmail());
        User user = USER_MAPPER.createUser(createUserRequest);

        Role userRole = roleRepository.findByUserRoleEquals(createUserRequest.getUserRole())
                .orElseThrow(() -> {
                    log.error("Role not found for role: {}", createUserRequest.getUserRole());
                    return new RuntimeException("Role Not Found");
                });

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        log.debug("Role {} assigned to user: {}", createUserRequest.getUserRole(), createUserRequest.getEmail());

        String encodedNewPassword = passwordEncoder.encode(createUserRequest.getPassword());
        user.setPassword(encodedNewPassword);
        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());
        return ResponseEntity.ok(USER_MAPPER.toUserDto(savedUser));
    }

    @Override
    public List<UserDto> getAllUsers() {
        log.info("Fetching all users");
        return USER_MAPPER.toUserDtoList(userRepository.findAll().stream().collect(Collectors.toList()));
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequest updateUserRequest) {
        log.info("Updating user with ID: {}", id);
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        USER_MAPPER.updateUserRequest(updateUserRequest,existingUser);
        return USER_MAPPER.toUserDto(userRepository.save(existingUser));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException(USER_NOT_FOUND));
        log.info("Retrieved  user {}", email);
        if (user!=null) {
            return UserDetailsImpl.build(user);
        }
        log.error("Retrieved  user not found {}", email);
        throw new UsernameNotFoundException(email);
    }
}
