package by.shumpanov.stove.stove_app_parent.constructor.util.mapper;

import by.shumpanov.stove.stove_app_parent.constructor.dto.ComponentDto;
import by.shumpanov.stove.stove_app_parent.constructor.model.Component;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComponentMapper {
    ComponentDto toDto(Component entity);
    List<ComponentDto> toDtoList(List<Component> entities);
}
