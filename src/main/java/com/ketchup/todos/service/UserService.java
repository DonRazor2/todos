package com.ketchup.todos.service;

import com.ketchup.todos.entity.User;

import java.nio.file.AccessDeniedException;

public interface UserService {
    User getUserInfo() throws AccessDeniedException;
}
