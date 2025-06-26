package by.shumpanov.stove.stove_app_parent.constructor.service;

import by.shumpanov.stove.stove_app_parent.constructor.dto.AddonDto;
import by.shumpanov.stove.stove_app_parent.constructor.dto.ComponentDto;
import by.shumpanov.stove.stove_app_parent.constructor.dto.ComponentOptionDto;
import by.shumpanov.stove.stove_app_parent.constructor.dto.StoveTypeDto;
import by.shumpanov.stove.stove_app_parent.constructor.repository.AddonRepository;
import by.shumpanov.stove.stove_app_parent.constructor.repository.ComponentOptionRepository;
import by.shumpanov.stove.stove_app_parent.constructor.repository.ComponentRepository;
import by.shumpanov.stove.stove_app_parent.constructor.repository.StoveTypeRepository;
import by.shumpanov.stove.stove_app_parent.constructor.util.mapper.AddonMapper;
import by.shumpanov.stove.stove_app_parent.constructor.util.mapper.ComponentMapper;
import by.shumpanov.stove.stove_app_parent.constructor.util.mapper.ComponentOptionMapper;
import by.shumpanov.stove.stove_app_parent.constructor.util.mapper.StoveTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructorDataService {
    private final AddonRepository addonRepository;
    private final ComponentOptionRepository componentOptionRepository;
    private final ComponentRepository componentRepository;
    private final StoveTypeRepository stoveTypeRepository;

    private final AddonMapper addonMapper;
    private final ComponentOptionMapper componentOptionMapper;
    private final ComponentMapper componentMapper;
    private final StoveTypeMapper stoveTypeMapper;

    public List<AddonDto> getAddons() {
        return addonMapper.toDtoList(addonRepository.findAll());
    }

    public List<ComponentOptionDto> getComponentOptionsByComponent(Long componentId) {
        return componentOptionMapper.toDtoList(componentOptionRepository.findByComponentId(componentId));
    }

    public List<ComponentDto> getComponentsByStoveType(Long stoveTypeId) {
        return componentMapper.toDtoList(componentRepository.findByStoveTypeId(stoveTypeId));
    }

    public List<StoveTypeDto> getStoveTypes() {
        return stoveTypeMapper.toDtoList(stoveTypeRepository.findAll());
    }
}
