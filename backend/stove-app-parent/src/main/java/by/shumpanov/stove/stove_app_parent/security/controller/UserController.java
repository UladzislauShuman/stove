package by.shumpanov.stove.stove_app_parent.security.controller;

import by.shumpanov.stove.stove_app_parent.security.dto.UserDto;
import by.shumpanov.stove.stove_app_parent.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserDto getMe(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getCurrentUser(userDetails.getUsername());
    }

    @PutMapping("/me")
    public UserDto updateCurrentUser(@AuthenticationPrincipal UserDetails userDetails,
                                     @RequestBody UserDto userDto) {
        return userService.updateCurrentUser(userDetails.getUsername(), userDto.getEmail());
    }
}
