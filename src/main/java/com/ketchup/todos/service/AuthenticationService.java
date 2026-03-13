package com.ketchup.todos.service;

import com.ketchup.todos.request.RegisterRequest;

public interface AuthenticationService {

    void register(RegisterRequest registerRequest) throws Exception;

}
