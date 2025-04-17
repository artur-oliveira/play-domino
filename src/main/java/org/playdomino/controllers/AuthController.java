package org.playdomino.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.playdomino.models.auth.dto.JwtResponse;
import org.playdomino.models.auth.dto.UserCreate;
import org.playdomino.models.auth.dto.UserToken;
import org.playdomino.services.auth.UserService;
import org.playdomino.services.auth.token.UserTokenService;
import org.playdomino.services.auth.verification.UserVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserTokenService userTokenService;
    private final UserVerificationService userVerificationService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserCreate user) {
        userService.create(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verify/{id}")
    public ResponseEntity<Void> verify(
            @PathVariable("id") String id
    ) {
        userVerificationService.verifyUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/token")
    public JwtResponse login(@Valid @RequestBody UserToken userToken) {
        return userTokenService.getToken(userToken);
    }

}

