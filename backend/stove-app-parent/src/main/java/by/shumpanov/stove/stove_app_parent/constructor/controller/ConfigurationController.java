package by.shumpanov.stove.stove_app_parent.constructor.controller;

import by.shumpanov.stove.stove_app_parent.constructor.dto.ConfigurationResponse;
import by.shumpanov.stove.stove_app_parent.constructor.dto.CreateConfigurationRequest;
import by.shumpanov.stove.stove_app_parent.constructor.service.ConfigurationService;
import by.shumpanov.stove.stove_app_parent.security.model.User;
import by.shumpanov.stove.stove_app_parent.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/configurations")
@RequiredArgsConstructor
public class ConfigurationController {

    private final ConfigurationService configurationService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ConfigurationResponse> createConfiguration(
            @RequestBody CreateConfigurationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findUserByEmail(userDetails.getUsername());

        ConfigurationResponse response = configurationService
                .create(request, currentUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationResponse> getConfiguration(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findUserByEmail(userDetails.getUsername());
        ConfigurationResponse response = configurationService.findById(id, currentUser);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfigurationResponse> updateConfiguration(
        @PathVariable Long id,
        @RequestBody CreateConfigurationRequest request,
        @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findUserByEmail(userDetails.getUsername());
        ConfigurationResponse response = configurationService.update(id, request, currentUser);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfiguration(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findUserByEmail(userDetails.getUsername());
        configurationService.delete(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/from-template/{templateId}")
    public ResponseEntity<ConfigurationResponse> createFromTemplate(
            @PathVariable Long templateId,
            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findUserByEmail(userDetails.getUsername());
        ConfigurationResponse response = configurationService.createFromTemplate(templateId, currentUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
