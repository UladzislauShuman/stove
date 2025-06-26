package by.shumpanov.stove.stove_app_parent.service;

import by.shumpanov.stove.stove_app_parent.dto.UserDto;
import by.shumpanov.stove.stove_app_parent.exception.UserNotFoundException;
import by.shumpanov.stove.stove_app_parent.model.User;
import by.shumpanov.stove.stove_app_parent.reposytory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким Email не найден"));

        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getUserRole())
                .build();
    }

    public UserDto updateCurrentUser(String email, String newEmail) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким Email не найден"));

        user.setEmail(newEmail);
        userRepository.save(user);

        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getUserRole())
                .build();
    }
}
