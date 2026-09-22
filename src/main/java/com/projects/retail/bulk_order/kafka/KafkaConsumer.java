package com.projects.retail.bulk_order.kafka;

import com.projects.retail.bulk_order.event.InventoryResultEvent;
import com.projects.retail.bulk_order.event.OrderConfirmedEvent;
import com.projects.retail.bulk_order.event.OrderCreatedEvent;
import com.projects.retail.bulk_order.event.PaymentResultEvent;
import com.projects.retail.bulk_order.entity.OrderEntity;
import com.projects.retail.bulk_order.exception.OutOfStockException;
import com.projects.retail.bulk_order.kafka.KafkaProducer;
import com.projects.retail.bulk_order.repository.OrderRepository;
import com.projects.retail.bulk_order.service.InventoryService;
import com.projects.retail.bulk_order.service.OrderService;
import com.projects.retail.bulk_order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;


    @KafkaListener(
            topics = "order-created",
            groupId = "inventory-service",
            concurrency = "3"
    )
    public void consumeOrderCreated(OrderCreatedEvent event) {

        System.out.println("Inventory Thread: " + Thread.currentThread().getName() + " | Order: " + event.getOrderId());

        try {

            OrderEntity order = orderRepository.findByOrderId(event.getOrderId());
            if (order == null) throw new IllegalArgumentException("Order not found: " + event.getOrderId());

            inventoryService.reserveStock(order);
            kafkaProducer.sendInventoryResult(
                    InventoryResultEvent.builder()
                            .orderId(event.getOrderId())
                            .success(true)
                            .message("Inventory reserved successfully")
                            .build()
            );

        } catch (OutOfStockException e) {

            System.out.println("Out of stock for order: " + event.getOrderId());

            orderService.markOrderFailed(event.getOrderId());

            kafkaProducer.sendInventoryResult(
                    InventoryResultEvent.builder()
                            .orderId(event.getOrderId())
                            .success(false)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @KafkaListener(
            topics = "inventory-result",
            groupId = "payment-service",
            concurrency = "3"
    )
    public void consumeInventoryResult(InventoryResultEvent event) {
        System.out.println("Payment Thread: " + Thread.currentThread().getName() + " | Order: " + event.getOrderId());

        if (!event.isSuccess()) return;
        paymentService.processPayment(event.getOrderId());
    }

    @KafkaListener(
            topics = "payment-result",
            groupId = "order-service",
            concurrency = "3"
    )
    public void consumePaymentResult(PaymentResultEvent event) {
        System.out.println("Order Thread: " + Thread.currentThread().getName() + " | Order: " + event.getOrderId());
        orderService.handlePaymentResult(event);
    }

    @KafkaListener(
            topics = "order-confirmed",
            groupId = "notification-service"
    )
    public void consumeForNotification(OrderConfirmedEvent event) {
        System.out.println("Notification Thread: " + Thread.currentThread().getName() + " | Order " + event.getOrderId() + " confirmed");
    }

    @KafkaListener(
            topics = "order-confirmed",
            groupId = "invoice-service"
    )
    public void consumeForInvoice(OrderConfirmedEvent event) {
        System.out.println("Invoice Thread: " + Thread.currentThread().getName() + " | Generating invoice for order " + event.getOrderId());
    }
}