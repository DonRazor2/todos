package com.ketchup.todos.service;

import com.ketchup.todos.entity.Authority;
import com.ketchup.todos.entity.User;
import com.ketchup.todos.repository.UserRepository;
import com.ketchup.todos.request.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
