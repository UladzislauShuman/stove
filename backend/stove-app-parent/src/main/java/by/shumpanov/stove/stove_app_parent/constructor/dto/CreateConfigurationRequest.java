package by.shumpanov.stove.stove_app_parent.constructor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateConfigurationRequest {

    @JsonProperty("stove_type_id")
    private Long stoveTypeId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("choices")
    private List<ChoiceDto> choices;
    @JsonProperty("addons")
    private List<AddonForConfigurationRequest> addons;
}
