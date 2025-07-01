package by.shumpanov.stove.stove_app_parent.constructor.service;

import by.shumpanov.stove.stove_app_parent.constructor.dto.*;
import by.shumpanov.stove.stove_app_parent.constructor.model.*;
import by.shumpanov.stove.stove_app_parent.constructor.repository.*;
import by.shumpanov.stove.stove_app_parent.constructor.util.mapper.ConfigurationMapper;
import by.shumpanov.stove.stove_app_parent.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;
    private final StoveTypeRepository stoveTypeRepository;
    private final ComponentOptionRepository componentOptionRepository;
    private final AddonRepository addonRepository;
    private final ConfigurationMapper configurationMapper;

    @Transactional
    public ConfigurationResponse create(CreateConfigurationRequest request, User author) {
        StoveType stoveType = stoveTypeRepository.findById(request.getStoveTypeId())
                .orElseThrow(() -> new RuntimeException("StoveType not found with id: " + request.getStoveTypeId()));

        Configuration configuration = Configuration.builder()
                .stoveType(stoveType)
                .author(author)
                .name(request.getName())
                .build();

        Configuration savedConfiguration = configurationRepository.save(configuration);
        updateChoicesAndAddons(savedConfiguration, request.getChoices(), request.getAddons());
        return buildConfigurationResponse(savedConfiguration);
    }

    @Transactional
    public ConfigurationResponse update(Long id, CreateConfigurationRequest request, User currentUser) {
        Configuration configuration = configurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuration not found with id: " + id));

        if (!configuration.getAuthor().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You do not have permission to update this configuration");
        }
        if (configuration.isLocked()) {
            throw new RuntimeException("Cannot update a locked configuration");
        }

        configuration.setName(request.getName());
        updateChoicesAndAddons(configuration, request.getChoices(), request.getAddons());
        Configuration updatedConfiguration = configurationRepository.save(configuration);
        return buildConfigurationResponse(updatedConfiguration);
    }

    @Transactional(readOnly = true)
    public ConfigurationResponse findById(Long id, User currentUser) {
        Configuration configuration = configurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuration not found with id: " + id));

        if (!configuration.getAuthor().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You do not have permission to view this configuration");
        }
        return buildConfigurationResponse(configuration);
    }

    @Transactional
    public void delete(Long id, User currentUser) {
        Configuration configuration = configurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuration not found with id: " + id));

        if (!configuration.getAuthor().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You do not have permission to delete this configuration");
        }
        if (configuration.isLocked()) {
            throw new RuntimeException("Cannot delete a locked configuration");
        }
        configurationRepository.delete(configuration);
    }

    @Transactional
    public ConfigurationResponse createFromTemplate(Long templateId, User author) {
        Configuration template = configurationRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found with id: " + templateId));

        if (!template.isTemplate()) {
            throw new IllegalArgumentException("Configuration with id " + templateId + " is not a template");
        }

        Configuration copy = Configuration.builder()
                .stoveType(template.getStoveType())
                .author(author)
                .name("Копия: " + template.getName())
                .build();
        Configuration savedCopy = configurationRepository.save(copy);

        List<ChoiceDto> templateChoices = template.getChoices().stream().map(c -> {
            ChoiceDto dto = ChoiceDto.builder()
                    .optionId(c.getOption().getId())
                    .quantity(c.getQuantity())
                    .build();
            return dto;
        }).collect(Collectors.toList());

        List<AddonForConfigurationRequest> templateAddons = template.getAddons().stream().map(a -> {
            AddonForConfigurationRequest dto = AddonForConfigurationRequest.builder()
                    .addonId(a.getAddon().getId())
                    .build();
            return dto;
        }).collect(Collectors.toList());

        updateChoicesAndAddons(savedCopy, templateChoices, templateAddons);
        return buildConfigurationResponse(savedCopy);
    }

    private void updateChoicesAndAddons(Configuration configuration, List<ChoiceDto> choices, List<AddonForConfigurationRequest> addons) {
        configuration.getChoices().clear();
        configuration.getAddons().clear();

        if (choices != null) {
            choices.forEach(choiceDto -> {
                ComponentOption option = componentOptionRepository.findById(choiceDto.getOptionId())
                        .orElseThrow(() -> new RuntimeException("Option not found with id: " + choiceDto.getOptionId()));
                ConfigurationChoiceId choiceId = new ConfigurationChoiceId(configuration.getId(), option.getId());
                configuration.getChoices().add(ConfigurationChoice.builder().id(choiceId).configuration(configuration).option(option).quantity(choiceDto.getQuantity()).build());
            });
        }

        if (addons != null) {
            addons.forEach(addonDto -> {
                Addon addon = addonRepository.findById(addonDto.getAddonId())
                        .orElseThrow(() -> new RuntimeException("Addon not found with id: " + addonDto.getAddonId()));
                ConfigurationAddonId addonId = new ConfigurationAddonId(configuration.getId(), addon.getId());
                configuration.getAddons().add(ConfigurationAddon.builder().id(addonId).configuration(configuration).addon(addon).build());
            });
        }
    }

    private ConfigurationResponse buildConfigurationResponse(Configuration configuration) {
        ConfigurationResponse dto = configurationMapper.toDto(configuration);
        dto.setTotalPrice(calculateTotalPrice(configuration));
        List<ChosenComponentDto> chosenComponents = configuration.getChoices().stream().map(choice -> {
            ChosenComponentDto chosenDto = ChosenComponentDto.builder()
                    .componentName(choice.getOption().getComponent().getName())
                    .chosenOption(new ComponentOptionDto(choice.getOption().getId(), choice.getOption().getName(), choice.getOption().getPriceModifier(), choice.getOption().getImageUrl(), choice.getOption().isDefault()))
                    .build();
            return chosenDto;
        }).collect(Collectors.toList());
        dto.setComponents(chosenComponents);
        return dto;
    }

    private BigDecimal calculateTotalPrice(Configuration configuration) {
        BigDecimal total = configuration.getStoveType().getBasePrice();
        if (configuration.getChoices() != null) {
            for (ConfigurationChoice choice : configuration.getChoices()) {
                total = total.add(choice.getOption().getPriceModifier());
            }
        }
        if (configuration.getAddons() != null) {
            for (ConfigurationAddon addon : configuration.getAddons()) {
                total = total.add(addon.getAddon().getPrice());
            }
        }
        return total;
    }
}