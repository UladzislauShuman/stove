package by.shumpanov.stove.stove_app_parent.constructor.service;

import by.shumpanov.stove.stove_app_parent.constructor.dto.*;
import by.shumpanov.stove.stove_app_parent.constructor.exception.ForbiddenException;
import by.shumpanov.stove.stove_app_parent.constructor.model.*;
import by.shumpanov.stove.stove_app_parent.constructor.repository.*;
import by.shumpanov.stove.stove_app_parent.constructor.util.mapper.ConfigurationMapper;
import by.shumpanov.stove.stove_app_parent.security.exception.ResourceNotFoundException;
import by.shumpanov.stove.stove_app_parent.security.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;
    private final StoveTypeRepository stoveTypeRepository;
    private final ComponentOptionRepository componentOptionRepository;
    private final AddonRepository addonRepository;
    private final ConfigurationMapper configurationMapper;

    @Transactional
    public ConfigurationResponse create(CreateConfigurationRequest request, User author) {
        log.info("Creating new configuration '{}' for user '{}'", request.getName(), author.getEmail());
        StoveType stoveType = stoveTypeRepository.findById(request.getStoveTypeId())
                .orElseThrow(() -> new RuntimeException("StoveType not found with id: " + request.getStoveTypeId()));

        Configuration configuration = Configuration.builder()
                .stoveType(stoveType)
                .author(author)
                .name(request.getName())
                .build();
        configurationRepository.saveAndFlush(configuration);
        log.debug("Saved initial configuration with id: {}", configuration.getId());

        updateChoicesAndAddons(configuration, request.getChoices(), request.getAddons());
        log.info("Successfully created configuration with id: {}", configuration.getId());
        return buildConfigurationResponse(configuration);
    }

    @Transactional
    public ConfigurationResponse update(Long id, CreateConfigurationRequest request, User currentUser) {
        log.info("Attempting to update configuration with id: {} by user '{}'", id, currentUser.getEmail());
        Configuration configuration = configurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuration not found with id: " + id));

        checkPermissionsAndLock(configuration, currentUser);

        configuration.setName(request.getName());
        updateChoicesAndAddons(configuration, request.getChoices(), request.getAddons());
        log.info("Successfully updated configuration with id: {}", id);
        return buildConfigurationResponse(configuration);
    }

    @Transactional(readOnly = true)
    public ConfigurationResponse findById(Long id, User currentUser) {
        Configuration configuration = configurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuration not found with id: " + id));

        if (!isUserConfigurationAuthor(configuration, currentUser)) {
            throw new RuntimeException("You do not have permission to view this configuration");
        }
        return buildConfigurationResponse(configuration);
    }

    @Transactional
    public void delete(Long id, User currentUser) {
        log.info("Attempting to delete configuration with id: {} by user '{}'", id, currentUser.getEmail());
        Configuration configuration = configurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuration not found with id: " + id));
        checkPermissionsAndLock(configuration, currentUser);
        configurationRepository.delete(configuration);
        log.info("Successfully deleted configuration with id: {}", id);
    }

    @Transactional
    public ConfigurationResponse createFromTemplate(Long templateId, User author) {
        log.info("User '{}' trying to create configuration from template with id: {}", author.getEmail(), templateId);

        Configuration template = findAndValidateTemplate(templateId);
        log.debug("Found template '{}'. Creating a copy for user '{}'.", template.getName(), author.getEmail());

        Configuration savedCopy = createConfigurationCopy(template, author);
        log.debug("Saved initial copy with new id: {}", savedCopy.getId());

        List<ChoiceDto> templateChoices = copyChoicesFromTemplate(template);
        List<AddonForConfigurationRequest> templateAddons = copyAddonsFromTemplate(template);
        log.debug("Template has {} choices and {} addons to copy.", templateChoices.size(), templateAddons.size());

        updateChoicesAndAddons(savedCopy, templateChoices, templateAddons);

        log.info("Successfully created configuration with id: {} from template id: {}", savedCopy.getId(), templateId);
        return buildConfigurationResponse(savedCopy);
    }

    private Configuration findAndValidateTemplate(Long templateId) {
        Configuration template = configurationRepository.findById(templateId)
                .orElseThrow(() -> {
                    log.error("Template not found with id: {}", templateId);
                    return new ResourceNotFoundException("Template not found with id: " + templateId);
                });

        if (!template.isTemplate()) {
            log.warn("Attempted to use a non-template configuration (id: {}) as a template.", templateId);
            throw new IllegalArgumentException("Configuration with id " + templateId + " is not a template");
        }
        return template;
    }

    private Configuration createConfigurationCopy(Configuration template, User author) {
        Configuration copy = Configuration.builder()
                .stoveType(template.getStoveType())
                .author(author)
                .name("Копия: " + template.getName())
                // isTemplate и isLocked по умолчанию false
                .build();
        return configurationRepository.save(copy);
    }

    private List<ChoiceDto> copyChoicesFromTemplate(Configuration template) {
        return template.getChoices().stream()
                .map(choice -> ChoiceDto.builder()
                        .optionId(choice.getOption().getId())
                        .quantity(choice.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    private List<AddonForConfigurationRequest> copyAddonsFromTemplate(Configuration template) {
        return template.getAddons().stream()
                .map(addon -> AddonForConfigurationRequest.builder()
                        .addonId(addon.getAddon().getId())
                        .build())
                .collect(Collectors.toList());
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

    private void checkPermissionsAndLock(Configuration configuration, User currentUser) {
        if (!isUserConfigurationAuthor(configuration, currentUser)) {
            log.warn("Forbidden access attempt: user '{}' tried to access configuration '{}' owned by user '{}'",
                    currentUser.getEmail(), configuration.getId(), configuration.getAuthor().getEmail());
            throw new ForbiddenException("You do not have permission to access this configuration");
        }
        if (configuration.isLocked()) {
            log.warn("Attempt to modify a locked configuration with id: {}", configuration.getId());
            throw new ForbiddenException("Cannot modify a locked configuration");
        }
    }

    private boolean isUserConfigurationAuthor(Configuration configuration, User user) {
        return configuration.getAuthor().getId().equals(user.getId());
    }

    private ConfigurationResponse buildConfigurationResponse(Configuration configuration) {
        ConfigurationResponse dto = configurationMapper.toResponseDto(configuration,calculateTotalPrice(configuration));
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

        public BigDecimal calculateTotalPrice(Configuration configuration) {
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