package com.ketchup.todos.service;

import com.ketchup.todos.request.AuthenticationRequest;
import com.ketchup.todos.request.RegisterRequest;
import com.ketchup.todos.response.AuthenticationResponse;

public interface AuthenticationService {

    void register(RegisterRequest registerRequest) throws Exception;

    AuthenticationResponse login(AuthenticationRequest request);
}
