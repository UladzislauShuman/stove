package by.shumpanov.stove.stove_app_parent.order.kafka;

import by.shumpanov.stove.stove_app_parent.order.dto.OrderCreatedEventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String ORDER_TOPIC = "new_orders";

    public void sendOrderCreatedEvent(OrderCreatedEventDto event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            log.info("Sending order created event to Kafka topic '{}': {}", ORDER_TOPIC, message);
            kafkaTemplate.send(ORDER_TOPIC, message);
        } catch (Exception e) {
            log.error("Failed to send order created event to Kafka for orderId: {}", event.getOrderId(), e);
        }
    }
}
