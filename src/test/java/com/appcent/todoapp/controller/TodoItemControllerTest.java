package com.appcent.todoapp.controller;
import com.appcent.todoapp.dto.TodoItemDto;
import com.appcent.todoapp.request.CreateTodoRequest;
import com.appcent.todoapp.request.UpdateTodoRequest;
import com.appcent.todoapp.service.TodoItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)

class TodoItemControllerTest {

    @InjectMocks
    private TodoItemController todoItemController;

    @Mock
    private TodoItemService todoItemService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(todoItemController).build();
    }

    @Test
    void testCreateTodoItem() throws Exception {
        TodoItemDto todoItemDto = TodoItemDto.builder()
                .id(1L)
                .title("Test TodoItem")
                .description("Test Description")
                .build();

        when(todoItemService.createTodo(anyLong(), any(CreateTodoRequest.class)))
                .thenReturn(todoItemDto);

        mockMvc.perform(post("/api/v1/appcent/todoitems/create")
                        .param("listId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Test TodoItem\", \"description\": \"Test Description\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test TodoItem"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }
    @Test
    void testGetTodoItemById() throws Exception {
        TodoItemDto todoItemDto = TodoItemDto.builder()
                .id(1L)
                .title("Test TodoItem")
                .description("Test Description")
                .build();

        when(todoItemService.getTodoById(anyLong())).thenReturn(todoItemDto);

        mockMvc.perform(get("/api/v1/appcent/todoitems/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test TodoItem"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }
    @Test
    void testUpdateTodoItem() throws Exception {
        TodoItemDto updatedTodoItemDto = TodoItemDto.builder()
                .id(1L)
                .title("Updated TodoItem")
                .description("Updated Description")
                .build();

        when(todoItemService.updateTodo(anyLong(), any(UpdateTodoRequest.class)))
                .thenReturn(updatedTodoItemDto);

        // PUT isteği ile TodoItem güncellemeyi test ediyoruz
        mockMvc.perform(put("/api/v1/appcent/todoitems/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Updated TodoItem\", \"description\": \"Updated Description\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated TodoItem"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }
    @Test
    void testDeleteTodoItem() throws Exception {
        doNothing().when(todoItemService).deleteTodo(anyLong());

        mockMvc.perform(delete("/api/v1/appcent/todoitems/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    void testGetTodoItemsByListId() throws Exception {
        List<TodoItemDto> todoItemDtos = List.of(
                TodoItemDto.builder().id(1L).title("Test Item 1").build(),
                TodoItemDto.builder().id(2L).title("Test Item 2").build()
        );

        when(todoItemService.getTodosByListId(anyLong())).thenReturn(todoItemDtos);

        mockMvc.perform(get("/api/v1/appcent/todoitems/list/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Test Item 1"))
                .andExpect(jsonPath("$[1].title").value("Test Item 2"));
    }



}
