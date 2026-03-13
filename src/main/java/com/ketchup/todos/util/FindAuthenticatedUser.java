package com.ketchup.todos.util;

import com.ketchup.todos.entity.User;

import java.nio.file.AccessDeniedException;

public interface FindAuthenticatedUser {
    User getAuthenticatedUser() throws AccessDeniedException;
}
