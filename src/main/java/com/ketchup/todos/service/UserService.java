package com.ketchup.todos.service;

import com.ketchup.todos.response.UserResponse;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

public interface UserService {

    UserResponse getUserInfo() throws AccessDeniedException;

    void deleteUser() throws ResponseStatusException, AccessDeniedException;
}
