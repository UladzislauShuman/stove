package by.shumpanov.stove.stove_app_parent.constructor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChoiceDto {
    @JsonProperty("option_id")
    private Long optionId;
    @JsonProperty("quantity")
    private Integer quantity;
}
