package com.appcent.todoapp.repository;

import com.appcent.todoapp.model.TodoItem;
import com.appcent.todoapp.model.TodoList;
import com.appcent.todoapp.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TodoItemRepositoryTest {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private TodoListRepository todoListRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUserId() {
        User user = User.builder().id(1L).build();
        user = userRepository.save(user);

        TodoList todoList1 = TodoList.builder()
                .title("Test List 1")
                .user(user)
                .build();
        todoList1 = todoListRepository.save(todoList1);

        TodoList todoList2 = TodoList.builder()
                .title("Test List 2")
                .user(user)
                .build();
        todoList2 = todoListRepository.save(todoList2);

        TodoItem todoItem1 = new TodoItem();
        todoItem1.setTitle("Test Item 1");
        todoItem1.setTodoList(todoList1);
        todoItemRepository.save(todoItem1);

        TodoItem todoItem2 = new TodoItem();
        todoItem2.setTitle("Test Item 2");
        todoItem2.setTodoList(todoList2);
        todoItemRepository.save(todoItem2);

        List<TodoList> todoLists = todoListRepository.findByUserId(user.getId());

        assertEquals(2, todoLists.size());

        List<TodoItem> itemsForList1 = todoItemRepository.findByTodoListId(todoList1.getId());
        assertEquals(1, itemsForList1.size());

        List<TodoItem> itemsForList2 = todoItemRepository.findByTodoListId(todoList2.getId());
        assertEquals(1, itemsForList2.size());
    }
}