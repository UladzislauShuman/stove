package by.shumpanov.stove.stove_app_parent.constructor.util.mapper;

// Файл: .../constructor/util/mapper/ConfigurationMapper.java
import by.shumpanov.stove.stove_app_parent.constructor.dto.ChosenComponentDto;
import by.shumpanov.stove.stove_app_parent.constructor.dto.ConfigurationResponse;
import by.shumpanov.stove.stove_app_parent.constructor.model.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConfigurationMapper {

    private final StoveTypeMapper stoveTypeMapper;
    private final AddonMapper addonMapper;
    private final ComponentOptionMapper componentOptionMapper;

    public ConfigurationResponse toResponseDto(Configuration configuration, BigDecimal totalPrice) {
        if (configuration == null) {
            return null;
        }

        var chosenComponents = configuration.getChoices().stream()
                .map(choice -> ChosenComponentDto.builder()
                        .componentName(choice.getOption().getComponent().getName())
                        .chosenOption(componentOptionMapper.toDto(choice.getOption()))
                        .build())
                .collect(Collectors.toList());

        var addons = configuration.getAddons().stream()
                .map(configAddon -> addonMapper.toDto(configAddon.getAddon()))
                .collect(Collectors.toList());

        return ConfigurationResponse.builder()
                .id(configuration.getId())
                .name(configuration.getName())
                .isTemplate(configuration.isTemplate())
                .isLocked(configuration.isLocked())
                .createdAt(configuration.getCreatedAt())
                .totalPrice(totalPrice)
                .stoveType(stoveTypeMapper.toDto(configuration.getStoveType()))
                .components(chosenComponents)
                .addons(addons)
                .build();
    }
}