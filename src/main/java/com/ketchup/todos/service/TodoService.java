package com.ketchup.todos.service;

import com.ketchup.todos.request.TodoRequest;
import com.ketchup.todos.response.TodoResponse;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface TodoService {
    List<TodoResponse> getAllTodos() throws AccessDeniedException, ResponseStatusException;

    TodoResponse createTodo(TodoRequest todoRequest) throws AccessDeniedException;

    TodoResponse toggleTodoCompletion(long id) throws AccessDeniedException;

    void deleteTodo(long id) throws AccessDeniedException;
}
