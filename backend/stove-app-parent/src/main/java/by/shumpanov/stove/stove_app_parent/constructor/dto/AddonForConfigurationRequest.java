package by.shumpanov.stove.stove_app_parent.constructor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddonForConfigurationRequest {
    @NotNull(message = "ID addon-а не может быть пустым")
    @JsonProperty("addon_id")
    private Long addonId;
}
