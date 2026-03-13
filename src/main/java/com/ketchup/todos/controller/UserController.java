package com.ketchup.todos.controller;

import com.ketchup.todos.entity.User;
import com.ketchup.todos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@Tag(name = "User REST API Endpoits", description = "Operations related to info about the current user")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User information", description = "Get current user info")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/info")
    public User getUserInfo() throws AccessDeniedException {
        log.info("GET /api/users/info called");

        User user = userService.getUserInfo();

        log.debug("User returned: {}", user);

        return user;
    }
}