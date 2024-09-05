package com.appcent.todoapp.controller;

import com.appcent.todoapp.dto.UserDto;
import com.appcent.todoapp.request.CreateUserRequest;
import com.appcent.todoapp.request.UpdateUserRequest;
import com.appcent.todoapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.appcent.todoapp.constants.Constant.*;

@RestController
@RequestMapping(API_PREFIX + API_VERSION_V1 + API_APPCENT + API_USER)
@RequiredArgsConstructor
@Tag(name = "CRUD REST APIs for User", description = "CRUD Rest APIs for User")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Create User REST API",
            description = "REST API to create new User inside ID3"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED",
                    content = @Content(
                            schema = @Schema(implementation = UserDto.class),
                            mediaType = "application/json")))
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid CreateUserRequest user) {
        return userService.createUser(user);
    }

    @Operation(
            summary = "Fetch User Details REST API",
            description = "REST API to fetch User details"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = UserDto.class),
                            mediaType = "application/json")))
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Update User Details REST API",
            description = "REST API to update User details"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = UserDto.class),
                            mediaType = "application/json")))
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest user) {
        return userService.updateUser(id, user);
    }
}
