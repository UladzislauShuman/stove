package by.shumpanov.stove.stove_app_parent.constructor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChosenComponentDto {
    @JsonProperty("component_name")
    private String componentName;
    @JsonProperty("chosen_option")
    private ComponentOptionDto chosenOption;
}
