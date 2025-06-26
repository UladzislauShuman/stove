package by.shumpanov.stove.stove_app_parent.constructor.util.mapper;

import by.shumpanov.stove.stove_app_parent.constructor.dto.ComponentOptionDto;
import by.shumpanov.stove.stove_app_parent.constructor.model.ComponentOption;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComponentOptionMapper {
    ComponentOptionDto toDto(ComponentOption entity);
    List<ComponentOptionDto> toDtoList(List<ComponentOption> entities);
}
