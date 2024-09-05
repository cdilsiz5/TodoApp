package com.appcent.todoapp.controller;

import com.appcent.todoapp.dto.TodoListDto;
import com.appcent.todoapp.request.CreateTodoListRequest;
import com.appcent.todoapp.request.UpdateTodoListRequest;
import com.appcent.todoapp.service.TodoListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc(addFilters = false)
class TodoListControllerTest {

    @InjectMocks
    private TodoListController todoListController;

    @Mock
    private TodoListService todoListService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(todoListController).build();
    }

    @Test
    void testCreateTodoList() throws Exception {
        TodoListDto todoListDto = new TodoListDto();
        todoListDto.setId(1L);
        todoListDto.setTitle("Test Todo List");

        when(todoListService.createTodoList(anyLong(), any(CreateTodoListRequest.class))).thenReturn(todoListDto);

        mockMvc.perform(post("/api/v1/appcent/todolists/create")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Test Todo List\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Todo List"));
    }

    @Test
    void testGetTodoListById() throws Exception {
        TodoListDto todoListDto = new TodoListDto();
        todoListDto.setId(1L);
        todoListDto.setTitle("Test Todo List");

        when(todoListService.getTodoListById(anyLong())).thenReturn(todoListDto);

        mockMvc.perform(get("/api/v1/appcent/todolists/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Todo List"));
    }

    @Test
    void testDeleteTodoList() throws Exception {
        doNothing().when(todoListService).deleteTodoList(anyLong());

        mockMvc.perform(delete("/api/v1/appcent/todolists/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(todoListService, times(1)).deleteTodoList(1L);
    }

    @Test
    void testGetTodoListsByUserId() throws Exception {
        List<TodoListDto> todoLists = new ArrayList<>();
        TodoListDto todoList1 = TodoListDto.builder().id(1L).title("Test List 1").build();
        TodoListDto todoList2 = TodoListDto.builder().id(2L).title("Test List 2").build();
        todoLists.add(todoList1);
        todoLists.add(todoList2);

        when(todoListService.getTodoListsByUserId(anyLong())).thenReturn(todoLists);

        mockMvc.perform(get("/api/v1/appcent/todolists/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Test List 1"))
                .andExpect(jsonPath("$[1].title").value("Test List 2"));

        verify(todoListService, times(1)).getTodoListsByUserId(1L);
    }

    @Test
    void testUpdateTodoList_Success() throws Exception {
        TodoListDto updatedTodoList = TodoListDto.builder()
                .id(1L)
                .title("Updated Title")
                .description("Updated Description")
                .build();

        when(todoListService.updateTodoList(anyLong(), any(UpdateTodoListRequest.class)))
                .thenReturn(updatedTodoList);

        mockMvc.perform(put("/api/v1/appcent/todolists/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Updated Title\", \"description\": \"Updated Description\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"));

        verify(todoListService, times(1)).updateTodoList(anyLong(), any(UpdateTodoListRequest.class));
    }
}