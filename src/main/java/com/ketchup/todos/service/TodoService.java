package com.ketchup.todos.service;

import com.ketchup.todos.request.TodoRequest;
import com.ketchup.todos.response.TodoResponse;

import java.nio.file.AccessDeniedException;

public interface TodoService {
    TodoResponse createTodo(TodoRequest todoRequest) throws AccessDeniedException;
}
