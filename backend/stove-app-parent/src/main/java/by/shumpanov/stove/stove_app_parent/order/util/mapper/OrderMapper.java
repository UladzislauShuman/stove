// Файл: .../order/util/mapper/OrderMapper.java

package by.shumpanov.stove.stove_app_parent.order.util.mapper;

import by.shumpanov.stove.stove_app_parent.constructor.service.ConfigurationService; // Нужен для расчета цены
import by.shumpanov.stove.stove_app_parent.constructor.util.mapper.ConfigurationMapper;
import by.shumpanov.stove.stove_app_parent.order.dto.OrderResponse;
import by.shumpanov.stove.stove_app_parent.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ConfigurationMapper configurationMapper;
    private final ConfigurationService configurationService;

    public OrderResponse toDto(Order order) {
        if (order == null) {
            return null;
        }


        BigDecimal totalPrice = configurationService.calculateTotalPrice(order.getConfiguration());

        var configurationResponse = configurationMapper.toResponseDto(order.getConfiguration(), totalPrice);

        return OrderResponse.builder()
                .id(order.getId())
                .status(order.getStatus())
                .finalPrice(order.getFinalPrice())
                .createdAt(order.getCreatedAt())
                .configuration(configurationResponse)
                .build();
    }

    public List<OrderResponse> toDtoList(List<Order> orders) {
        if (orders == null) {
            return null;
        }
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}