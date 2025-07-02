package by.shumpanov.stove.stove_app_parent.order.dto;

import by.shumpanov.stove.stove_app_parent.constructor.dto.ConfigurationResponse;
import by.shumpanov.stove.stove_app_parent.order.model.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("status")
    private Order.OrderStatus status;
    @JsonProperty("final_price")
    private BigDecimal finalPrice;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("configuration")
    private ConfigurationResponse configuration;
}
