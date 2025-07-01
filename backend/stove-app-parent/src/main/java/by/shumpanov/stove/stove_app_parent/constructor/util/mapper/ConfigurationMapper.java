package by.shumpanov.stove.stove_app_parent.constructor.util.mapper;

import by.shumpanov.stove.stove_app_parent.constructor.dto.AddonDto;
import by.shumpanov.stove.stove_app_parent.constructor.dto.ConfigurationResponse;
import by.shumpanov.stove.stove_app_parent.constructor.model.Configuration;
import by.shumpanov.stove.stove_app_parent.constructor.model.ConfigurationAddon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StoveTypeMapper.class, ComponentOptionMapper.class, AddonMapper.class})
public interface ConfigurationMapper {

    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "components", ignore = true)
    @Mapping(source = "addons", target = "addons")
    ConfigurationResponse toDto(Configuration entity);

    @Mapping(source = "addon", target = ".")
    AddonDto mapConfigurationAddonToAddonDto(ConfigurationAddon source);
}