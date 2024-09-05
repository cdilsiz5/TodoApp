package com.appcent.todoapp.mapper;

import com.appcent.todoapp.dto.TodoListDto;
import com.appcent.todoapp.model.TodoList;
import com.appcent.todoapp.request.CreateTodoListRequest;
import com.appcent.todoapp.request.UpdateTodoListRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper(componentModel = "spring")

public interface TodoListMapper {

    TodoListMapper TODO_LIST_MAPPER= Mappers.getMapper(TodoListMapper.class);

    TodoListDto toTodoListDto(TodoList todoList);

    List<TodoListDto> toTodoListDtoList(List<TodoList> todoLists);

    TodoList createTodoList(CreateTodoListRequest request);

    void updateTodoListRequest(UpdateTodoListRequest request, @MappingTarget TodoList todoList);

}
