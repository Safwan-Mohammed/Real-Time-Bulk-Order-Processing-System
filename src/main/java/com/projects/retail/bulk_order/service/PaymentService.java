package com.projects.retail.bulk_order.service;

import com.projects.retail.bulk_order.entity.OrderEntity;
import com.projects.retail.bulk_order.entity.PaymentEntity;
import com.projects.retail.bulk_order.enums.TxnStatus;
import com.projects.retail.bulk_order.event.OrderCreatedEvent;
import com.projects.retail.bulk_order.event.PaymentResultEvent;
import com.projects.retail.bulk_order.kafka.KafkaProducer;
import com.projects.retail.bulk_order.repository.OrderRepository;
import com.projects.retail.bulk_order.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final KafkaProducer kafkaProducer;

    public void processPayment(OrderCreatedEvent event) {
        OrderEntity order = orderRepository.findByOrderId(event.getOrderId());

        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + event.getOrderId());
        }

        PaymentEntity payment = PaymentEntity.builder()
                .orderId(order.getOrderId())
                .amount(order.getTotalAmount())
                .txnStatus(TxnStatus.PENDING)
                .build();

        payment = paymentRepository.save(payment);

        boolean paymentSuccess = true;

        payment.setTxnStatus(paymentSuccess ? TxnStatus.SUCCESS : TxnStatus.FAILED);
        paymentRepository.save(payment);

        kafkaProducer.sendPaymentResult(
                PaymentResultEvent.builder()
                        .orderId(order.getOrderId())
                        .transactionId(payment.getTxnId())
                        .success(paymentSuccess)
                        .message(paymentSuccess ? "Simulated payment successful" : "Simulated payment failed")
                        .build()
        );
    }
}