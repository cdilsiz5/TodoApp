package com.appcent.todoapp.service;


import com.appcent.todoapp.dto.TodoItemDto;
import com.appcent.todoapp.request.CreateTodoRequest;
import com.appcent.todoapp.request.UpdateTodoRequest;

import java.util.List;

public interface TodoItemService {
    TodoItemDto createTodo(Long listId, CreateTodoRequest request);
    TodoItemDto updateTodo(Long todoId, UpdateTodoRequest request);
    void deleteTodo(Long todoId);
    TodoItemDto getTodoById(Long todoId);
    List<TodoItemDto> getTodosByListId(Long listId);
}
