package com.appcent.todoapp.controller;

import com.appcent.todoapp.dto.TodoItemDto;
import com.appcent.todoapp.request.CreateTodoRequest;
import com.appcent.todoapp.request.UpdateTodoRequest;
import com.appcent.todoapp.service.TodoItemService;
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
@RequestMapping(API_PREFIX + API_VERSION_V1 + API_APPCENT + API_TODO_ITEM)
@RequiredArgsConstructor
@Tag(name = "CRUD REST APIs for TodoItem", description = "CRUD REST APIs for managing TodoItems")
public class TodoItemController {

    private final TodoItemService todoItemService;

    @Operation(
            summary = "Create TodoItem REST API",
            description = "REST API to create a new TodoItem"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED",
                    content = @Content(
                            schema = @Schema(implementation = TodoItemDto.class),
                            mediaType = "application/json")))
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TodoItemDto> createTodoItem(
            @RequestParam Long listId,
            @RequestBody @Valid CreateTodoRequest todoRequest) {
        TodoItemDto createdTodoItem = todoItemService.createTodo(listId, todoRequest);
        return new ResponseEntity<>(createdTodoItem, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get TodoItem By ID REST API",
            description = "REST API to fetch TodoItem details by ID"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = TodoItemDto.class),
                            mediaType = "application/json")))
    @GetMapping("/{todoId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TodoItemDto> getTodoItemById(@PathVariable Long todoId) {
        TodoItemDto todoItem = todoItemService.getTodoById(todoId);
        return new ResponseEntity<>(todoItem, HttpStatus.OK);
    }

    @Operation(
            summary = "Update TodoItem REST API",
            description = "REST API to update an existing TodoItem"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = TodoItemDto.class),
                            mediaType = "application/json")))
    @PutMapping("/{todoId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TodoItemDto> updateTodoItem(
            @PathVariable Long todoId,
            @RequestBody @Valid UpdateTodoRequest updateRequest) {
        TodoItemDto updatedTodoItem = todoItemService.updateTodo(todoId, updateRequest);
        return new ResponseEntity<>(updatedTodoItem, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete TodoItem REST API",
            description = "REST API to delete a TodoItem by ID"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "204",
                    description = "HTTP Status NO CONTENT"
            ))
    @DeleteMapping("/{todoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTodoItem(@PathVariable Long todoId) {
        todoItemService.deleteTodo(todoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Get TodoItems By List ID REST API",
            description = "REST API to fetch all TodoItems for a specific TodoList"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = TodoItemDto.class),
                            mediaType = "application/json")))
    @GetMapping("/list/{listId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TodoItemDto>> getTodoItemsByListId(@PathVariable Long listId) {
        List<TodoItemDto> todoItems = todoItemService.getTodosByListId(listId);
        return new ResponseEntity<>(todoItems, HttpStatus.OK);
    }
}
