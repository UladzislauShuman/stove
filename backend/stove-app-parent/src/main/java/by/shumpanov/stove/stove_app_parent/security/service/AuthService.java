package by.shumpanov.stove.stove_app_parent.security.service;

import by.shumpanov.stove.stove_app_parent.security.dto.AuthResponse;
import by.shumpanov.stove.stove_app_parent.security.dto.LoginRequest;
import by.shumpanov.stove.stove_app_parent.security.dto.RegisterRequest;
import by.shumpanov.stove.stove_app_parent.security.model.User;
import by.shumpanov.stove.stove_app_parent.security.repository.UserRepository;
import by.shumpanov.stove.stove_app_parent.security.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService( UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService,
        AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .email(registerRequest.getEmail())
                .fullName(registerRequest.getFullName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .userRole(User.UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword())
        );

        String token = jwtService.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
}
