package com.appcent.todoapp.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTodoRequest {
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;

}
