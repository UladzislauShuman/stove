package by.shumpanov.stove.stove_app_parent.constructor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChoiceDto {

    @NotNull(message = "ID варианта не может быть пустым")
    @JsonProperty("option_id")
    private Long optionId;

    @JsonProperty("quantity")
    private Integer quantity;
}
