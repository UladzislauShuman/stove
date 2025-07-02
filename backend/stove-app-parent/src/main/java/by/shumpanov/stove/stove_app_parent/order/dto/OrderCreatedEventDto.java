package by.shumpanov.stove.stove_app_parent.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderCreatedEventDto {
    @NotNull(message = "OrderID не может быть пустым")
    @JsonProperty("order_id")
    private Long orderId;

    @NotNull(message = "userId не может быть пустым")
    @JsonProperty("user_id")
    private Long userId;

    @NotBlank(message = "customerName не может быть пустым")
    @JsonProperty("customer_name")
    private String customerName;

    @NotBlank(message = "customerPhone не может быть пустым")
    @JsonProperty("customer_phone")
    private String customerPhone;

    @NotNull(message = "estimatedPrice не может быть пустым")
    @JsonProperty("estimated_price")
    private BigDecimal estimatedPrice;

    @NotNull(message = "createdAt не может быть пустым")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
