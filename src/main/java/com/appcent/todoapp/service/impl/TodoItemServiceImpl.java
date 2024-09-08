package com.appcent.todoapp.service.impl;

import com.appcent.todoapp.dto.TodoItemDto;
import com.appcent.todoapp.exception.TodoItemNotFoundException;
import com.appcent.todoapp.model.TodoItem;
import com.appcent.todoapp.model.TodoList;
import com.appcent.todoapp.model.enums.PriorityLevel;
import com.appcent.todoapp.model.enums.TodoStatus;
import com.appcent.todoapp.repository.TodoItemRepository;
import com.appcent.todoapp.repository.TodoListRepository;
import com.appcent.todoapp.request.CreateTodoRequest;
import com.appcent.todoapp.request.UpdateTodoRequest;
import com.appcent.todoapp.service.TodoItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.appcent.todoapp.mapper.TodoItemMapper.TODO_ITEM_MAPPER;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoItemServiceImpl implements TodoItemService {

    private final TodoItemRepository todoItemRepository;
    private final TodoListRepository todoListRepository;

    @Override
    public TodoItemDto createTodo(Long listId, CreateTodoRequest request) {
        log.info("Creating a new TodoItem for TodoList with ID: {}", listId);

        TodoList todoList = todoListRepository.findById(listId)
                .orElseThrow(() -> {
                    log.error("TodoList with ID: {} not found!", listId);
                    return new RuntimeException("TodoList not found!");
                });

        TodoItem todoItem = toTodoItem(request);
        try {
            todoItem.setPriority(PriorityLevel.valueOf(request.getPriority().toUpperCase()));
        } catch (IllegalArgumentException e) {
            log.error("Invalid priority level: {}", request.getPriority());
            throw new RuntimeException("Invalid priority level: " + request.getPriority());
        }

        try {
            todoItem.setStatus(TodoStatus.valueOf(request.getStatus().toUpperCase()));
        } catch (IllegalArgumentException e) {
            log.error("Invalid status: {}", request.getStatus());
            throw new RuntimeException("Invalid status: " + request.getStatus());
        }
        todoItem.setTodoList(todoList);

        TodoItem savedTodoItem = todoItemRepository.save(todoItem);
        log.info("TodoItem with ID: {} created successfully for TodoList with ID: {}", savedTodoItem.getId(), listId);

        return TODO_ITEM_MAPPER.toTodoItemDto(savedTodoItem);
    }

    @Override
    public TodoItemDto updateTodo(Long todoId, UpdateTodoRequest request) {
        log.info("Updating TodoItem with ID: {}", todoId);

        TodoItem todoItem = todoItemRepository.findById(todoId)
                .orElseThrow(() -> {
                    log.error("TodoItem with ID: {} not found!", todoId);
                    return new RuntimeException("TodoItem not found!");
                });

        TODO_ITEM_MAPPER.updateTodoItem(request, todoItem);
        TodoItem updatedTodoItem = todoItemRepository.save(todoItem);

        log.info("TodoItem with ID: {} updated successfully", updatedTodoItem.getId());

        return TODO_ITEM_MAPPER.toTodoItemDto(updatedTodoItem);
    }

    @Override
    public void deleteTodo(Long todoId) {
        log.info("Deleting TodoItem with ID: {}", todoId);

        if (!todoItemRepository.existsById(todoId)) {
            log.error("TodoItem with ID: {} not found, deletion failed!", todoId);
            throw new TodoItemNotFoundException("TodoItem not found!");
        }

        todoItemRepository.deleteById(todoId);
        log.info("TodoItem with ID: {} deleted successfully", todoId);
    }

    @Override
    public TodoItemDto getTodoById(Long todoId) {
        log.info("Fetching TodoItem with ID: {}", todoId);

        TodoItem todoItem = todoItemRepository.findById(todoId)
                .orElseThrow(() -> {
                    log.error("TodoItem with ID: {} not found!", todoId);
                    return new TodoItemNotFoundException("TodoItem not found!");
                });

        log.info("TodoItem with ID: {} fetched successfully", todoId);
        return TODO_ITEM_MAPPER.toTodoItemDto(todoItem);
    }

    @Override
    public List<TodoItemDto> getTodosByListId(Long listId) {
        log.info("Fetching all TodoItems for TodoList with ID: {}", listId);

        List<TodoItem> todoItems = todoItemRepository.findByTodoListId(listId);

        log.info("Fetched {} TodoItems for TodoList with ID: {}", todoItems.size(), listId);
        return TODO_ITEM_MAPPER.toTodoItemDtoList(todoItems);
    }
    private TodoItem toTodoItem(CreateTodoRequest request) {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle(request.getTitle());
        todoItem.setDescription(request.getDescription());
        return todoItem;
    }
}
