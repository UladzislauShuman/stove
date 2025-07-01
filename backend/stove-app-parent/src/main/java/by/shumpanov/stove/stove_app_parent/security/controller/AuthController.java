package by.shumpanov.stove.stove_app_parent.security.controller;

import by.shumpanov.stove.stove_app_parent.security.dto.AuthResponse;
import by.shumpanov.stove.stove_app_parent.security.dto.LoginRequest;
import by.shumpanov.stove.stove_app_parent.security.dto.RegisterRequest;
import by.shumpanov.stove.stove_app_parent.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
