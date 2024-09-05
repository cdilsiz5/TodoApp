package com.appcent.todoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoListDto {

    private Long id;
    private String title;
    private String description;
    private List<TodoItemDto> todoItems;

 }
