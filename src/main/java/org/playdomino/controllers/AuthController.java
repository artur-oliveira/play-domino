package org.playdomino.controllers;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.UserCreate;
import org.playdomino.services.auth.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody UserCreate user) {
        return userService.create(user);
    }

    @PostMapping("/token")
    public User login(@RequestBody UserCreate user) {
        return userService.create(user);
    }

}

