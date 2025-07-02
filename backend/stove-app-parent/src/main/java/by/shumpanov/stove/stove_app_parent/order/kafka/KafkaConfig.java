package by.shumpanov.stove.stove_app_parent.order.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public static final String NEW_ORDERS_TOPIC = "new_orders";

    @Bean
    public NewTopic newOrdersTopic() {
        return TopicBuilder.name(NEW_ORDERS_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
