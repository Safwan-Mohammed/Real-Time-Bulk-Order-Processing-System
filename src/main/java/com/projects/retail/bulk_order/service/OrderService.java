package com.projects.retail.bulk_order.service;

import com.projects.retail.bulk_order.dto.request.order.OrderRequestDTO;
import com.projects.retail.bulk_order.dto.response.GeneralResponseDTO;
import com.projects.retail.bulk_order.dto.response.order.OrderResponseDTO;
import com.projects.retail.bulk_order.entity.OrderEntity;
import com.projects.retail.bulk_order.enums.OrderStatus;
import com.projects.retail.bulk_order.event.OrderConfirmedEvent;
import com.projects.retail.bulk_order.event.OrderCreatedEvent;
import com.projects.retail.bulk_order.event.PaymentResultEvent;
import com.projects.retail.bulk_order.kafka.KafkaProducer;
import com.projects.retail.bulk_order.mapper.OrderMapper;
import com.projects.retail.bulk_order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final KafkaProducer kafkaProducer;

    private <T>ResponseEntity<T> returnResponseEntity(HttpStatus status, T message){
        return ResponseEntity.status(status).body(message);
    }

    public ResponseEntity<GeneralResponseDTO> fetchAllOrders(){
        try{
            List<OrderEntity> orderEntities = orderRepository.findAll();
            List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
            for(OrderEntity order : orderEntities)
                orderResponseDTOS.add(orderMapper.convertEntityToDTO(order));
            return returnResponseEntity(HttpStatus.OK, GeneralResponseDTO.builder().message("SUCCESS").data(orderResponseDTOS).build());
        } catch (Exception e) {
            return returnResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, GeneralResponseDTO.builder().message("Error Occurred : "+e.getMessage()).build());
        }
    }

    public ResponseEntity<GeneralResponseDTO> fetchOrder(UUID orderId){
        try{
            OrderEntity order = orderRepository.findByOrderId(orderId);
            if(order == null){
                return returnResponseEntity(HttpStatus.NOT_FOUND, GeneralResponseDTO.builder().message("Invalid OrderId").build());
            }
            return returnResponseEntity(HttpStatus.OK, GeneralResponseDTO.builder().message("SUCCESS").data(orderMapper.convertEntityToDTO(order)).build());
        } catch (Exception e) {
            return returnResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, GeneralResponseDTO.builder().message("Error Occurred : "+e.getMessage()).build());
        }
    }

    public ResponseEntity<?> createOrder(OrderRequestDTO dto){
        try{
            OrderEntity order = orderMapper.convertDTOToEntity(dto);
            orderRepository.save(order);
            kafkaProducer.sendOrderCreated(OrderCreatedEvent.builder().email(order.getOrderEmail()).orderId(order.getOrderId()).totalAmount(order.getTotalAmount()).build());
            return returnResponseEntity(HttpStatus.CREATED, GeneralResponseDTO.builder().message("SUCCESS").data(orderMapper.convertEntityToDTO(order)).build());
        } catch (Exception e) {
            return returnResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, GeneralResponseDTO.builder().message("Error Occurred : "+e.getMessage()).build());
        }
    }

    public void markOrderFailed(UUID orderId) {

        OrderEntity order = orderRepository.findByOrderId(orderId);
        if (order == null) throw new IllegalArgumentException("Order not found: " + orderId);

        order.setStatus(OrderStatus.FAILED);
        orderRepository.save(order);
    }

    public void handlePaymentResult(PaymentResultEvent event) {
        OrderEntity order = orderRepository.findByOrderId(event.getOrderId());

        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + event.getOrderId());
        }

        if (!event.isSuccess()) {
            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);
            return;
        }

        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        kafkaProducer.sendOrderConfirmed(
                OrderConfirmedEvent.builder()
                        .orderId(order.getOrderId())
                        .email(order.getOrderEmail())
                        .totalAmount(order.getTotalAmount())
                        .build()
        );
    }
}
