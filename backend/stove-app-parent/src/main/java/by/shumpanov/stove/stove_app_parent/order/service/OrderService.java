package by.shumpanov.stove.stove_app_parent.order.service;

import by.shumpanov.stove.stove_app_parent.constructor.exception.ForbiddenException;
import by.shumpanov.stove.stove_app_parent.constructor.model.Configuration;
import by.shumpanov.stove.stove_app_parent.constructor.repository.ConfigurationRepository;
import by.shumpanov.stove.stove_app_parent.order.dto.CreateOrderRequest;
import by.shumpanov.stove.stove_app_parent.order.dto.OrderCreatedEventDto;
import by.shumpanov.stove.stove_app_parent.order.dto.OrderResponse;
import by.shumpanov.stove.stove_app_parent.order.exception.ConfigurationAlreadyUsedException;
import by.shumpanov.stove.stove_app_parent.order.kafka.KafkaProducerService;
import by.shumpanov.stove.stove_app_parent.order.model.Order;
import by.shumpanov.stove.stove_app_parent.order.repository.OrderRepository;
import by.shumpanov.stove.stove_app_parent.order.util.mapper.OrderMapper;
import by.shumpanov.stove.stove_app_parent.security.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ConfigurationRepository configurationRepository;
    private final KafkaProducerService kafkaProducerService;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request, User user) {
        log.info("User '{}' is creating an order from configuration id {}", user.getEmail(), request.getConfigurationId());
        Configuration configuration = findAndValidateConfiguration(request.getConfigurationId(), user);

        BigDecimal finalPrice = calculateTotalPrice(configuration);

        Order order = buildOrder(request, user, configuration, finalPrice);
        orderRepository.save(order);
        log.info("Order with id {} created successfully", order.getId());
        lockConfiguration(configuration);
        sendNotification(order);

        return orderMapper.toDto(order);
    }

    @Transactional
    public List<OrderResponse> findUserOrders(User user) {
        log.info("Fetching all orders for user '{}'", user.getEmail());
        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orderMapper.toDtoList(orders);
    }

    private Configuration findAndValidateConfiguration(Long configId, User user) {
        Configuration configuration = configurationRepository.findById(configId)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration not found with id: " + configId));
        if (!isUserConfigurationAuthor(configuration, user)) {
            throw new ForbiddenException("You do not have permission to use this configuration");
        }
        if (configuration.isLocked()) {
            throw new ConfigurationAlreadyUsedException("Configuration with id " + configId + " is already used in an order and is locked.");
        }
        return configuration;
    }

    private Order buildOrder(CreateOrderRequest request, User user, Configuration configuration, BigDecimal price) {
        return Order.builder()
                .configuration(configuration)
                .user(user)
                .status(Order.OrderStatus.PLACED)
                .customerName(request.getCustomerName())
                .customerPhone(request.getCustomerPhone())
                .objectAddress(request.getObjectAddress())
                .customerComment(request.getCustomerComment())
                .finalPrice(price)
                .build();
    }

    private void lockConfiguration(Configuration configuration) {
        configuration.setLocked(true);
        configurationRepository.save(configuration);
        log.info("Configuration with id {} has been locked.", configuration.getId());
    }

    private void sendNotification(Order order) {
        OrderCreatedEventDto event = OrderCreatedEventDto.builder()
                .orderId(order.getId())
                .userId(order.getUser().getId())
                .customerName(order.getCustomerName())
                .customerPhone(order.getCustomerPhone())
                .estimatedPrice(order.getFinalPrice())
                .createdAt(order.getCreatedAt())
                .build();
        kafkaProducerService.sendOrderCreatedEvent(event);
    }

    private BigDecimal calculateTotalPrice(Configuration configuration) {
        return BigDecimal.ZERO;
    }

    private boolean isUserConfigurationAuthor(Configuration configuration, User user) {
        return configuration.getAuthor().getId().equals(user.getId());
    }
}
