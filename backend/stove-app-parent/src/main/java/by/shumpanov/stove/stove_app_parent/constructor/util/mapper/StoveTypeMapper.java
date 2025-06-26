package by.shumpanov.stove.stove_app_parent.constructor.util.mapper;

import by.shumpanov.stove.stove_app_parent.constructor.dto.StoveTypeDto;
import by.shumpanov.stove.stove_app_parent.constructor.model.StoveType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoveTypeMapper {
    StoveTypeDto toDto(StoveType entity);
    List<StoveTypeDto> toDtoList(List<StoveType> entities);
}
