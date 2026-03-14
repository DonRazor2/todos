package com.ketchup.todos.service;

import com.ketchup.todos.entity.Todo;
import com.ketchup.todos.entity.User;
import com.ketchup.todos.repository.TodoRepository;
import com.ketchup.todos.request.TodoRequest;
import com.ketchup.todos.response.TodoResponse;
import com.ketchup.todos.util.FindAuthenticatedUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;

    public TodoServiceImpl(TodoRepository todoRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.todoRepository = todoRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional
    public TodoResponse createTodo(TodoRequest todoRequest) throws AccessDeniedException {
        User user = findAuthenticatedUser.getAuthenticatedUser();

        Todo todo = new Todo(
                todoRequest.getTittle(),
                todoRequest.getDescription(),
                todoRequest.getPriority(),
                false,
                user
        );

        Todo savedTodo = todoRepository.save(todo);

        return new TodoResponse(
                savedTodo.getId(),
                savedTodo.getTittle(),
                savedTodo.getDescription(),
                savedTodo.getPriority(),
                savedTodo.isComplete());
    }
}
