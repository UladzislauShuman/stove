package by.shumpanov.stove.stove_app_parent.constructor.controller;

import by.shumpanov.stove.stove_app_parent.constructor.dto.AddonDto;
import by.shumpanov.stove.stove_app_parent.constructor.dto.ComponentDto;
import by.shumpanov.stove.stove_app_parent.constructor.dto.ComponentOptionDto;
import by.shumpanov.stove.stove_app_parent.constructor.dto.StoveTypeDto;
import by.shumpanov.stove.stove_app_parent.constructor.service.ConstructorDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/constructor-data")
@RequiredArgsConstructor
public class ConstructorDataController {

    private final ConstructorDataService constructorDataService;

    @GetMapping("/stove-types")
    public List<StoveTypeDto> getStoveTypes() {
        return constructorDataService.getStoveTypes();
    }

    @GetMapping("/stove-types/{id}/components")
    public List<ComponentDto> getComponents(@PathVariable Long id) {
        return constructorDataService.getComponentsByStoveType(id);
    }

    @GetMapping("/components/{id}/options")
    public List<ComponentOptionDto> getConcreteComponentOptions(@PathVariable Long id) {
        return constructorDataService.getComponentOptionsByComponent(id);
    }

    @GetMapping("/addons")
    public List<AddonDto> getAddons() {
        return constructorDataService.getAddons();
    }
}
