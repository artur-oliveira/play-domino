package org.playdomino.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructorl
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        // Criptografar senha
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userRepository.save(user);
        return "Usuário registrado com sucesso!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // Aqui você vai depender do Spring Security para fazer a autenticação
        return "Login feito com sucesso!";
    }
}

