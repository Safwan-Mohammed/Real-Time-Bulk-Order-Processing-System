package com.projects.retail.bulk_order.kafka;

import com.projects.retail.bulk_order.event.OrderConfirmedEvent;
import com.projects.retail.bulk_order.event.OrderCreatedEvent;
import com.projects.retail.bulk_order.event.PaymentResultEvent;
import com.projects.retail.bulk_order.service.OrderService;
import com.projects.retail.bulk_order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @KafkaListener(topics = "order-created", groupId = "payment-service")
    public void consumeOrderCreated(OrderCreatedEvent event) {
        System.out.println("Payment: processing order " + event.getOrderId());

        orderService.markOrderProcessing(event.getOrderId());
        paymentService.processPayment(event);
    }

    @KafkaListener(topics = "payment-result", groupId = "order-service")
    public void consumePaymentResult(PaymentResultEvent event) {
        System.out.println("Order: received payment result for " + event.getOrderId());

        orderService.handlePaymentResult(event);
    }

    @KafkaListener(topics = "order-confirmed", groupId = "notification-service")
    public void consumeForNotification(OrderConfirmedEvent event) {
        System.out.println("Notification: Order " + event.getOrderId() + " confirmed");
    }

    @KafkaListener(topics = "order-confirmed", groupId = "invoice-service")
    public void consumeForInvoice(OrderConfirmedEvent event) {
        System.out.println("Invoice: Generating invoice for order "
                + event.getOrderId());
    }
}