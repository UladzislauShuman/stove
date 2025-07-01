package by.shumpanov.stove.stove_app_parent.security.service;

import by.shumpanov.stove.stove_app_parent.security.dto.UserDto;
import by.shumpanov.stove.stove_app_parent.security.exception.EmailAlreadyExistsException;
import by.shumpanov.stove.stove_app_parent.security.exception.UserNotFoundException;
import by.shumpanov.stove.stove_app_parent.security.model.User;
import by.shumpanov.stove.stove_app_parent.security.repository.UserRepository;
import by.shumpanov.stove.stove_app_parent.security.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto getCurrentUser(String email) {
        log.debug("Fetching current user data for email: {}", email);
        User user = findUserByEmail(email);
        return userMapper.toDto(user);
    }

    public UserDto updateCurrentUser(String currentEmail, UserDto updateUserDto) {
        log.info("Attempting to update user with email: {}", currentEmail);
        User userToUpdate = findUserByEmail(currentEmail);

        userToUpdate.setFullName(updateUserDto.getFullName());
        userToUpdate.setPhoneNumber(updateUserDto.getPhoneNumber());
        String newEmail = updateUserDto.getEmail();
        if (newEmail != null && !newEmail.equals(currentEmail)) {
            if (isEmailFree(newEmail)) {
                throw new EmailAlreadyExistsException("Email " + newEmail + " уже занят");
            }
            userToUpdate.setEmail(newEmail);
        }

        User updatedUser = userRepository.save(userToUpdate);
        log.info("User with ID {} updated successfully. New email is '{}'", updatedUser.getId(), updatedUser.getEmail());

        return  userMapper.toDto(updatedUser);
    }

    public User findUserByEmail(String email) {
        log.debug("Finding user entity by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с email: " + email + " не найдена"));
    }

    private boolean isEmailFree(String email) {
        boolean isPresent = userRepository.findByEmail(email).isPresent();
        log.debug("Checking if email '{}' is free. Result: {}", email, !isPresent);
        return isPresent;
    }
}
