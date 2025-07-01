package by.shumpanov.stove.stove_app_parent.constructor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateConfigurationRequest {

    @NotBlank(message = "ID типа строения не может быть пустым")
    @JsonProperty("stove_type_id")
    private Long stoveTypeId;

    @NotBlank(message = "Название конфигурации не может быть пустым")
    @JsonProperty("name")
    private String name;

    @NotEmpty(message = "Должен быть хотя бы один выбор компонента")
    @Valid
    @JsonProperty("choices")
    private List<ChoiceDto> choices;

    @Valid
    @JsonProperty("addons")
    private List<AddonForConfigurationRequest> addons;
}
