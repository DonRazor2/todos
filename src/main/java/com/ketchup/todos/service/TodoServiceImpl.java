package com.ketchup.todos.service;

import com.ketchup.todos.entity.Todo;
import com.ketchup.todos.entity.User;
import com.ketchup.todos.repository.TodoRepository;
import com.ketchup.todos.request.TodoRequest;
import com.ketchup.todos.response.TodoResponse;
import com.ketchup.todos.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;

    public TodoServiceImpl(TodoRepository todoRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.todoRepository = todoRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos() throws AccessDeniedException, ResponseStatusException {
        User user = findAuthenticatedUser.getAuthenticatedUser();

        return todoRepository.findByOwner(user)
                .stream()
                .map(this::convertToTodoResponse)
                .toList();
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

        return convertToTodoResponse(savedTodo);
    }

    @Override
    @Transactional
    public TodoResponse toggleTodoCompletion(long id) throws AccessDeniedException {
        User user = findAuthenticatedUser.getAuthenticatedUser();

        Optional<Todo> todo = todoRepository.findByIdAndOwner(id, user);

        if (todo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }

        todo.ifPresent(availableTodo -> {
            availableTodo.setComplete(!availableTodo.isComplete());
            todoRepository.save(availableTodo);
        });

        return convertToTodoResponse(todo.get());
    }

    @Transactional
    @Override
    public void deleteTodo(long id) throws AccessDeniedException {
        User user = findAuthenticatedUser.getAuthenticatedUser();

        Optional<Todo> todo = todoRepository.findByIdAndOwner(id, user);

        if (todo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }

        todo.ifPresent(todoRepository::delete);
    }

    private TodoResponse convertToTodoResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTittle(),
                todo.getDescription(),
                todo.getPriority(),
                todo.isComplete()
        );
    }
}
