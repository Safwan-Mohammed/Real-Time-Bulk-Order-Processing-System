package com.projects.retail.bulk_order.kafka;

import com.projects.retail.bulk_order.event.InventoryResultEvent;
import com.projects.retail.bulk_order.event.OrderConfirmedEvent;
import com.projects.retail.bulk_order.event.OrderCreatedEvent;
import com.projects.retail.bulk_order.event.PaymentResultEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderCreated(OrderCreatedEvent event) {

        kafkaTemplate.send(
                "order-created",
                event.getOrderId().toString(),
                event
        );
    }

    public void sendInventoryResult(InventoryResultEvent event) {

        kafkaTemplate.send(
                "inventory-result",
                event.getOrderId().toString(),
                event
        );
    }

    public void sendPaymentResult(PaymentResultEvent event) {

        kafkaTemplate.send(
                "payment-result",
                event.getOrderId().toString(),
                event
        );
    }

    public void sendOrderConfirmed(OrderConfirmedEvent event) {

        kafkaTemplate.send(
                "order-confirmed",
                event.getOrderId().toString(),
                event
        );
    }
}