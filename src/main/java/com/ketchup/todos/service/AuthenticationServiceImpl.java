package com.ketchup.todos.service;

import com.ketchup.todos.entity.Authority;
import com.ketchup.todos.entity.User;
import com.ketchup.todos.repository.UserRepository;
import com.ketchup.todos.request.AuthenticationRequest;
import com.ketchup.todos.request.RegisterRequest;
import com.ketchup.todos.response.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public void register(RegisterRequest registerRequest) throws Exception {

        if (isEmailTaken(registerRequest.getEmail())) {
            throw new Exception("Email is taken"); // bad practice to show user that email is taken! Repair when exceptions are implemented
        }

        User user = buildNewUser(registerRequest);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse login(AuthenticationRequest request) {

        // This throws an error if it fails
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        String jwtToken = jwtService.generateToken(new HashMap<>(), user);

        return new AuthenticationResponse(jwtToken);
    }

    private boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private List<Authority> initialAuthority() {
        boolean isFirstUser = userRepository.count() == 0;

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_EMPLOYEE"));

        // WORKAROUND BECAUSE WE NEED AT LEAST 1 ADMIN IN DB
        if (isFirstUser) {
            authorities.add(new Authority("ROLE_ADMIN"));
        }

        return authorities;
    }

    private User buildNewUser(RegisterRequest registerRequest) {
        final User newUser = new User();
        newUser.setId(0);
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setAuthorities(initialAuthority());

        return newUser;
    }
}
