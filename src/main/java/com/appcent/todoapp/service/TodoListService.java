package com.appcent.todoapp.service;


import com.appcent.todoapp.dto.TodoListDto;
import com.appcent.todoapp.request.CreateTodoListRequest;
import com.appcent.todoapp.request.UpdateTodoListRequest;

import java.util.List;

public interface TodoListService {
    TodoListDto createTodoList(Long userId, CreateTodoListRequest request);
    TodoListDto updateTodoList(Long listId, UpdateTodoListRequest request);
    void deleteTodoList(Long listId);
    TodoListDto getTodoListById(Long listId);
    List<TodoListDto> getTodoListsByUserId(Long userId);
}