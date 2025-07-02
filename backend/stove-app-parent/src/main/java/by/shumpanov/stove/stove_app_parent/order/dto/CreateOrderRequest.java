package by.shumpanov.stove.stove_app_parent.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderRequest {
    @NotNull(message = "configurationId не может быть пустым")
    @JsonProperty("configuration_id")
    private Long configurationId;

    @NotBlank(message = "customerName не может быть пустым")
    @JsonProperty("customer_name")
    private String customerName;

    @NotBlank(message = "customerPhone не может быть пустым")
    @JsonProperty("customer_phone")
    private String customerPhone;

    @JsonProperty("object_address")
    private String objectAddress;
    @JsonProperty("customer_comment")
    private String customerComment;
}
