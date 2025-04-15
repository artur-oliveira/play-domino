package org.playdomino.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.JwtResponse;
import org.playdomino.models.auth.dto.UserCreate;
import org.playdomino.models.auth.dto.UserToken;
import org.playdomino.services.auth.UserService;
import org.playdomino.services.auth.token.UserTokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserTokenService userTokenService;

    @PostMapping("/register")
    public User register(@Valid @RequestBody UserCreate user) {
        return userService.create(user);
    }

    @PostMapping("/token")
    public JwtResponse login(@Valid @RequestBody UserToken userToken) {
        return userTokenService.getToken(userToken);
    }

}

