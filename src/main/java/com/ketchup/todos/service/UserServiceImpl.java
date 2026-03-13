package com.ketchup.todos.service;

import com.ketchup.todos.entity.Authority;
import com.ketchup.todos.entity.User;
import com.ketchup.todos.repository.UserRepository;
import com.ketchup.todos.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo() throws AccessDeniedException {

        final User user = getUser();

        return new UserResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getEmail(),
                user.getAuthorities().stream().map(auth -> (Authority) auth).toList());
    }

    @Override
    public void deleteUser() throws ResponseStatusException, AccessDeniedException {
        final User user = getUser();

        // isLastAdmin?
        if (isLastAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin cannot delete itself");
        }

        userRepository.delete(user);
    }

    private User getUser() throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new AccessDeniedException("Authentication required");
        }

        return (User) authentication.getPrincipal();
    }

    private boolean isLastAdmin(User user) {
        boolean isAdmin = user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            long adminCount = userRepository.countAdminUsers();
            return adminCount <= 1;
        }

        return false;
    }
}
