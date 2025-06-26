package by.shumpanov.stove.stove_app_parent.constructor.util.mapper;

import by.shumpanov.stove.stove_app_parent.constructor.dto.AddonDto;
import by.shumpanov.stove.stove_app_parent.constructor.model.Addon;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddonMapper {
    AddonDto toDto(Addon entity);
    List<AddonDto> toDtoList(List<Addon> entities);
}
