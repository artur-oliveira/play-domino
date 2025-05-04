package org.playdomino.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.models.auth.dto.CurrentUserDTO;
import org.playdomino.models.auth.dto.UserUpdate;
import org.playdomino.services.auth.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public CurrentUserDTO getCurrentUser() {
        return CurrentUserDTO.of(UserUtils.currentUser());
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateCurrentUser(@Valid @RequestBody UserUpdate user) {
        userService.update(user);
        return ResponseEntity.noContent().build();
    }
}
