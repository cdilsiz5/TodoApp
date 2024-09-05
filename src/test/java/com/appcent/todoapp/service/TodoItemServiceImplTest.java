package com.appcent.todoapp.service;

import com.appcent.todoapp.exception.TodoItemNotFoundException;

import com.appcent.todoapp.repository.TodoItemRepository;
import com.appcent.todoapp.repository.TodoListRepository;
import com.appcent.todoapp.request.CreateTodoRequest;
import com.appcent.todoapp.request.UpdateTodoRequest;
import com.appcent.todoapp.service.impl.TodoItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TodoItemServiceImplTest {

    @InjectMocks
    private TodoItemServiceImpl todoItemService;

    @Mock
    private TodoItemRepository todoItemRepository;

    @Mock
    private TodoListRepository todoListRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateTodo_TodoListNotFound() {
        CreateTodoRequest createTodoRequest = new CreateTodoRequest();
        when(todoListRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> todoItemService.createTodo(1L, createTodoRequest));
    }


    @Test
    void testUpdateTodo_NotFound() {
        UpdateTodoRequest updateRequest = new UpdateTodoRequest();
        when(todoItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> todoItemService.updateTodo(1L, updateRequest));
    }

    @Test
    void testDeleteTodo_Success() {
        when(todoItemRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(todoItemRepository).deleteById(anyLong());

        todoItemService.deleteTodo(1L);
        verify(todoItemRepository, times(1)).existsById(1L);
        verify(todoItemRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTodo_NotFound() {
        when(todoItemRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(TodoItemNotFoundException.class, () -> todoItemService.deleteTodo(1L));
    }



    @Test
    void testGetTodoById_NotFound() {
        when(todoItemRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(TodoItemNotFoundException.class, () -> todoItemService.getTodoById(1L));
    }


}
