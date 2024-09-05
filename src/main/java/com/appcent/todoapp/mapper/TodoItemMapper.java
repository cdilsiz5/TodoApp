package com.appcent.todoapp.mapper;

import com.appcent.todoapp.dto.TodoItemDto;
import com.appcent.todoapp.model.TodoItem;
import com.appcent.todoapp.request.CreateTodoRequest;
import com.appcent.todoapp.request.UpdateTodoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoItemMapper {

    TodoItemMapper TODO_ITEM_MAPPER = Mappers.getMapper(TodoItemMapper.class);

    TodoItemDto toTodoItemDto(TodoItem todoItem);

    List<TodoItemDto> toTodoItemDtoList(List<TodoItem> todoItems);

    TodoItem toTodoItem(CreateTodoRequest request);

    void updateTodoItem(UpdateTodoRequest request, @MappingTarget TodoItem todoItem);
}
