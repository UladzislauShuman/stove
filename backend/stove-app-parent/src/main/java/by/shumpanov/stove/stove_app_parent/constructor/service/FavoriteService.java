package by.shumpanov.stove.stove_app_parent.constructor.service;

import by.shumpanov.stove.stove_app_parent.constructor.dto.ConfigurationResponse;
import by.shumpanov.stove.stove_app_parent.constructor.model.Configuration;
import by.shumpanov.stove.stove_app_parent.constructor.model.Favorite;
import by.shumpanov.stove.stove_app_parent.constructor.model.FavoriteId;
import by.shumpanov.stove.stove_app_parent.constructor.repository.ConfigurationRepository;
import by.shumpanov.stove.stove_app_parent.constructor.repository.FavoriteRepository;
import by.shumpanov.stove.stove_app_parent.constructor.util.mapper.ConfigurationMapper;
import by.shumpanov.stove.stove_app_parent.security.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ConfigurationRepository configurationRepository;
    private final ConfigurationMapper configurationMapper;
    private final ConfigurationService configurationService;

    @Transactional(readOnly = true)
    public List<ConfigurationResponse> getUserFavorites(User user) {
        log.info("Fetching favorites for user : {}", user.getEmail());
        List<Favorite> favorites = favoriteRepository.findByUserId(user.getId());

        return favorites.stream()
                .map(Favorite::getConfiguration)
                .map(configuration -> {
                    BigDecimal totalPrice = configurationService.calculateTotalPrice(configuration);
                    return configurationMapper.toResponseDto(configuration, totalPrice);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void addFavorite(Long configurationId, User user) {
        log.info("User '{}' attempting to add configuration '{}' to favorites", user.getEmail(), configurationId);

        Configuration configuration = configurationRepository.findById(configurationId)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration not found with id: " + configurationId));

        FavoriteId favoriteId =  new FavoriteId(user.getId(), configurationId);

        if (favoriteRepository.findById(favoriteId).isPresent()) {
            log.warn("Configuration '{}' is already in favorites for user '{}'", configurationId, user.getEmail());
            return;
        }

        Favorite favorite = Favorite.builder()
                .id(favoriteId)
                .user(user)
                .configuration(configuration)
                .build();

        favoriteRepository.save(favorite);
        log.info("Configuration '{}' added to favorites for user '{}'", configurationId, user.getEmail());
    }

    @Transactional
    public void removeFavorite(Long configurationId, User user) {
        log.info("User '{}' attempting to remove configuration '{}' from favorites", user.getEmail(), configurationId);
        FavoriteId favoriteId = new FavoriteId(user.getId(), configurationId);

        if (!favoriteRepository.existsById(favoriteId)) {
            log.warn("Attempt to remove non-existent favorite. ConfigId: {}, UserId: {}", configurationId, user.getId());
            throw new ResourceNotFoundException("This configuration is not in your favorites.");
        }

        favoriteRepository.deleteById(favoriteId);
        log.info("Configuration '{}' removed from favorites for user '{}'", configurationId, user.getEmail());
    }
}
