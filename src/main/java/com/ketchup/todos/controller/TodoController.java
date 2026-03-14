package com.ketchup.todos.controller;

import com.ketchup.todos.request.TodoRequest;
import com.ketchup.todos.response.TodoResponse;
import com.ketchup.todos.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Tag(name = "Todo REST API Endpoints", description = "Operations managing user's todos")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(summary = "Create todo for user", description = "Create todo for the signed in user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TodoResponse createTodo(@Valid @RequestBody TodoRequest request) throws AccessDeniedException {
        return todoService.createTodo(request);
    }

    @Operation(summary = "Get all todos for user", description = "Fetch all todos from signed in user")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TodoResponse> getAllTodos() throws AccessDeniedException {
        return todoService.getAllTodos();
    }

    @Operation(summary = "Toggle todo completion", description = "Toggle todo to completed or in progress")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public TodoResponse toggleTodoCompletion(@PathVariable @Min(1) long id) throws AccessDeniedException {
        return todoService.toggleTodoCompletion(id);
    }

    @Operation(summary = "Delete a todo for user", description = "Remove a todo from the database for the logged in user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable @Min(1) long id) throws AccessDeniedException {
        todoService.deleteTodo(id);
    }
}
