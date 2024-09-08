package com.appcent.todoapp.service;

import com.appcent.todoapp.dto.TodoListDto;
import com.appcent.todoapp.exception.TodoListNotFoundException;
import com.appcent.todoapp.exception.UserNotFoundException;
import com.appcent.todoapp.mapper.TodoListMapper;
import com.appcent.todoapp.model.TodoList;
import com.appcent.todoapp.model.User;
import com.appcent.todoapp.repository.TodoListRepository;
import com.appcent.todoapp.repository.UserRepository;
import com.appcent.todoapp.request.CreateTodoListRequest;
import com.appcent.todoapp.request.UpdateTodoListRequest;
import com.appcent.todoapp.service.impl.TodoListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoListServiceImplTest {

    @Mock
    private TodoListRepository todoListRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TodoListMapper todoListMapper;

    @InjectMocks
    private TodoListServiceImpl todoListService;

    private User mockUser;
    private TodoList mockTodoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        // Mock data
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Test User");

        mockTodoList = new TodoList();
        mockTodoList.setId(1L);
        mockTodoList.setTitle("Test Todo List");
        mockTodoList.setUser(mockUser);
    }

    @Test
    void testCreateTodoList_UserNotFound() {
        // Mock the scenario where user is not found
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        CreateTodoListRequest request = new CreateTodoListRequest();
        request.setTitle("Test");

        assertThrows(UserNotFoundException.class, () -> {
            // Artık request param değil, path variable olarak 1L gönderiliyor
            todoListService.createTodoList(1L, request);
        });

        verify(userRepository, times(1)).findById(1L);
        verifyNoInteractions(todoListRepository);
    }

    @Test
    void testCreateTodoList_Success() {
        // Mock user and todo list creation
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(todoListRepository.save(any(TodoList.class))).thenReturn(mockTodoList);
        when(todoListMapper.createTodoList(any(CreateTodoListRequest.class))).thenReturn(mockTodoList);
        when(todoListMapper.toTodoListDto(any(TodoList.class))).thenReturn(new TodoListDto());

        CreateTodoListRequest request = new CreateTodoListRequest();
        request.setTitle("Test Todo List");

        TodoListDto todoListDto = todoListService.createTodoList(1L, request);

        assertNotNull(todoListDto);
        verify(todoListRepository, times(1)).save(any(TodoList.class));
    }

    @Test
    void testUpdateTodoList_TodoListNotFound() {
        // Mock the scenario where TodoList is not found
        when(todoListRepository.findById(anyLong())).thenReturn(Optional.empty());

        UpdateTodoListRequest request = new UpdateTodoListRequest();
        request.setTitle("Updated Title");

        assertThrows(TodoListNotFoundException.class, () -> {
            // Artık request param değil, path variable olarak 1L gönderiliyor
            todoListService.updateTodoList(1L, request);
        });

        verify(todoListRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(todoListRepository);
    }

    @Test
    void testUpdateTodoList_Success() {
        // Mock todo list update
        when(todoListRepository.findById(anyLong())).thenReturn(Optional.of(mockTodoList));
        when(todoListRepository.save(any(TodoList.class))).thenReturn(mockTodoList);
        when(todoListMapper.toTodoListDto(any(TodoList.class))).thenReturn(new TodoListDto());

        UpdateTodoListRequest request = new UpdateTodoListRequest();
        request.setTitle("Updated Title");

        TodoListDto updatedTodoList = todoListService.updateTodoList(1L, request);

        assertNotNull(updatedTodoList);
        verify(todoListRepository, times(1)).save(any(TodoList.class));
    }

    @Test
    void testDeleteTodoList_TodoListNotFound() {
        // Mock the scenario where TodoList does not exist
        when(todoListRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(TodoListNotFoundException.class, () -> {
            // Artık request param değil, path variable olarak 1L gönderiliyor
            todoListService.deleteTodoList(1L);
        });

        verify(todoListRepository, times(1)).existsById(1L);
    }

    @Test
    void testDeleteTodoList_Success() {
        // Mock successful deletion
        when(todoListRepository.existsById(anyLong())).thenReturn(true);

        todoListService.deleteTodoList(1L);

        verify(todoListRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetTodoListById_TodoListNotFound() {
        // Mock the scenario where TodoList does not exist
        when(todoListRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TodoListNotFoundException.class, () -> {
            // Artık request param değil, path variable olarak 1L gönderiliyor
            todoListService.getTodoListById(1L);
        });

        verify(todoListRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTodoListById_Success() {
        // Mock fetching a TodoList by ID
        when(todoListRepository.findById(anyLong())).thenReturn(Optional.of(mockTodoList));
        when(todoListMapper.toTodoListDto(any(TodoList.class))).thenReturn(new TodoListDto());

        TodoListDto todoListDto = todoListService.getTodoListById(1L);

        assertNotNull(todoListDto);
        verify(todoListRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTodoListsByUserId_Success() {
        // Mock fetching TodoLists by user ID
        when(todoListRepository.findByUserId(anyLong())).thenReturn(Collections.singletonList(mockTodoList));
        when(todoListMapper.toTodoListDtoList(anyList())).thenReturn(Collections.singletonList(new TodoListDto()));

        List<TodoListDto> todoLists = todoListService.getTodoListsByUserId(1L);

        assertFalse(todoLists.isEmpty());
        verify(todoListRepository, times(1)).findByUserId(1L);
    }
}
