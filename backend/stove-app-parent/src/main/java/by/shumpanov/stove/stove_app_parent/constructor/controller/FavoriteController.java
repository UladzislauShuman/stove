package by.shumpanov.stove.stove_app_parent.constructor.controller;

import by.shumpanov.stove.stove_app_parent.constructor.dto.ConfigurationResponse;
import by.shumpanov.stove.stove_app_parent.constructor.service.FavoriteService;
import by.shumpanov.stove.stove_app_parent.security.model.User;
import by.shumpanov.stove.stove_app_parent.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ConfigurationResponse>> getFavorites(
            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findUserByEmail(userDetails.getUsername());
        List<ConfigurationResponse> favorites = favoriteService.getUserFavorites(currentUser);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping("/{configurationId}")
    public ResponseEntity<Void> addFavorites(
            @PathVariable Long configurationId,
            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findUserByEmail(userDetails.getUsername());
        favoriteService.addFavorite(configurationId, currentUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{configurationId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable Long configurationId,
            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findUserByEmail(userDetails.getUsername());
        favoriteService.removeFavorite(configurationId, currentUser);
        return ResponseEntity.noContent().build();
    }
}
