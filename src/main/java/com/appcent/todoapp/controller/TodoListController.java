package com.appcent.todoapp.controller;

import com.appcent.todoapp.dto.TodoListDto;
import com.appcent.todoapp.request.CreateTodoListRequest;
import com.appcent.todoapp.request.UpdateTodoListRequest;
import com.appcent.todoapp.service.TodoListService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.appcent.todoapp.constants.Constant.*;

@RestController
@RequestMapping(API_PREFIX + API_VERSION_V1 + API_APPCENT +API_TODO_LIST )
@RequiredArgsConstructor
@Tag(name = "CRUD REST APIs for TodoList", description = "CRUD Rest APIs for managing TodoLists")
public class TodoListController {

    private final TodoListService todoListService;

    @Operation(summary = "Create TodoList REST API", description = "REST API to create a new TodoList")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "HTTP Status CREATED",
                    content = @Content(schema = @Schema(implementation = TodoListDto.class), mediaType = "application/json"))
    })
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TodoListDto> createTodoList(@RequestParam Long userId, @RequestBody @Valid CreateTodoListRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoListService.createTodoList(userId, request));
    }

    @Operation(summary = "Get TodoList By ID REST API", description = "REST API to fetch a TodoList by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK",
                    content = @Content(schema = @Schema(implementation = TodoListDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "HTTP Status NOT FOUND")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TodoListDto> getTodoListById(@PathVariable Long id) {
        return ResponseEntity.ok(todoListService.getTodoListById(id));
    }

    @Operation(summary = "Get All TodoLists by User ID REST API", description = "REST API to fetch all TodoLists of a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK",
                    content = @Content(schema = @Schema(implementation = TodoListDto.class), mediaType = "application/json"))
    })
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoListDto> getTodoListsByUserId(@PathVariable Long userId) {
        return todoListService.getTodoListsByUserId(userId);
    }

    @Operation(summary = "Update TodoList REST API", description = "REST API to update a TodoList by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK",
                    content = @Content(schema = @Schema(implementation = TodoListDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "HTTP Status NOT FOUND")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TodoListDto> updateTodoList(@PathVariable Long id, @RequestBody @Valid UpdateTodoListRequest request) {
        return ResponseEntity.ok(todoListService.updateTodoList(id, request));
    }

    @Operation(summary = "Delete TodoList REST API", description = "REST API to delete a TodoList by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "HTTP Status NO CONTENT"),
            @ApiResponse(responseCode = "404", description = "HTTP Status NOT FOUND")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTodoList(@PathVariable Long id) {
        todoListService.deleteTodoList(id);
        return ResponseEntity.noContent().build();
    }
}
