package by.shumpanov.stove.stove_app_parent.security.service;

import by.shumpanov.stove.stove_app_parent.security.dto.AuthResponse;
import by.shumpanov.stove.stove_app_parent.security.dto.LoginRequest;
import by.shumpanov.stove.stove_app_parent.security.dto.RegisterRequest;
import by.shumpanov.stove.stove_app_parent.security.model.User;
import by.shumpanov.stove.stove_app_parent.security.repository.UserRepository;
import by.shumpanov.stove.stove_app_parent.security.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest registerRequest) {
        log.info("Attempting to register new user with email: {}", registerRequest.getEmail());
        User user = User.builder()
                .email(registerRequest.getEmail())
                .fullName(registerRequest.getFullName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .userRole(User.UserRole.CUSTOMER)
                .build();

        userRepository.save(user); // если уже есть, то выброситься DataIntegrityViolationException из-за ограничения unique

        log.info("User with email '{}' registered successfully. Generating token.", user.getEmail());

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) { // BadCredentialsException, UserNotFoundException
        log.info("Attempting to authenticate user with email: {}", request.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword())
        );
        log.info("User with email '{}' authenticated successfully. Generating token.", request.getEmail());
        String token = jwtService.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
}
