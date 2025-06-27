package by.shumpanov.stove.stove_app_parent.constructor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ConfigurationResponse {
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("is_template")
    private boolean isTemplate = false;
    @JsonProperty("is_locked")
    private boolean isLocked;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("total_price")
    private BigDecimal totalPrice;
    @JsonProperty("stove_type")
    private StoveTypeDto stoveType;

    @JsonProperty("components")
    private List<ChosenComponentDto> components;
    @JsonProperty("addons")
    private List<AddonDto> addons;

}
