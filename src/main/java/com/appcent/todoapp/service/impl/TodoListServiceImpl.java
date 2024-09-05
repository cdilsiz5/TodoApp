package com.appcent.todoapp.service.impl;

import com.appcent.todoapp.dto.TodoListDto;
import com.appcent.todoapp.exception.TodoListNotFoundException;
import com.appcent.todoapp.exception.UserNotFoundException;
import com.appcent.todoapp.model.TodoList;
import com.appcent.todoapp.model.User;
import com.appcent.todoapp.repository.TodoListRepository;
import com.appcent.todoapp.repository.UserRepository;
import com.appcent.todoapp.request.CreateTodoListRequest;
import com.appcent.todoapp.request.UpdateTodoListRequest;
import com.appcent.todoapp.service.TodoListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.appcent.todoapp.mapper.TodoListMapper.TODO_LIST_MAPPER;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoListServiceImpl implements TodoListService {

    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;

    @Override
    public TodoListDto createTodoList(Long userId, CreateTodoListRequest request) {
        log.info("Creating a new TodoList for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with ID: {} not found!", userId);
                    return new UserNotFoundException("User not found!");
                });

        TodoList todoList = TODO_LIST_MAPPER.createTodoList(request);
        todoList.setUser(user);

        TodoList savedList = todoListRepository.save(todoList);
        log.info("TodoList with ID: {} created successfully for user ID: {}", savedList.getId(), userId);

        return TODO_LIST_MAPPER.toTodoListDto(savedList);
    }

    @Override
    public TodoListDto updateTodoList(Long listId, UpdateTodoListRequest request) {
        log.info("Updating TodoList with ID: {}", listId);

        TodoList todoList = todoListRepository.findById(listId)
                .orElseThrow(() -> {
                    log.error("TodoList with ID: {} not found!", listId);
                    return new TodoListNotFoundException("TodoList not found!");
                });

        TODO_LIST_MAPPER.updateTodoListRequest(request, todoList);
        TodoList updatedList = todoListRepository.save(todoList);

        log.info("TodoList with ID: {} updated successfully", updatedList.getId());
        return TODO_LIST_MAPPER.toTodoListDto(updatedList);
    }

    @Override
    public void deleteTodoList(Long listId) {
        log.info("Deleting TodoList with ID: {}", listId);

        if (!todoListRepository.existsById(listId)) {
            log.error("TodoList with ID: {} not found, deletion failed!", listId);
            throw new TodoListNotFoundException("TodoList not found!");
        }

        todoListRepository.deleteById(listId);
        log.info("TodoList with ID: {} deleted successfully", listId);
    }

    @Override
    public TodoListDto getTodoListById(Long listId) {
        log.info("Fetching TodoList with ID: {}", listId);

        TodoList todoList = todoListRepository.findById(listId)
                .orElseThrow(() -> {
                    log.error("TodoList with ID: {} not found!", listId);
                    return new TodoListNotFoundException("TodoList not found!");
                });

        log.info("TodoList with ID: {} fetched successfully", listId);
        return TODO_LIST_MAPPER.toTodoListDto(todoList);
    }

    @Override
    public List<TodoListDto> getTodoListsByUserId(Long userId) {
        log.info("Fetching all TodoLists for user ID: {}", userId);

        List<TodoList> todoLists = todoListRepository.findByUserId(userId);

        log.info("Fetched {} TodoLists for user ID: {}", todoLists.size(), userId);
        return TODO_LIST_MAPPER.toTodoListDtoList(todoLists);
    }
}
