package com.projects.retail.bulk_order.mapper;

import com.projects.retail.bulk_order.dto.request.order.OrderItemRequestDTO;
import com.projects.retail.bulk_order.dto.request.order.OrderRequestDTO;
import com.projects.retail.bulk_order.dto.response.order.OrderResponseDTO;
import com.projects.retail.bulk_order.entity.OrderEntity;
import com.projects.retail.bulk_order.entity.OrderItemEntity;
import com.projects.retail.bulk_order.entity.ProductEntity;
import com.projects.retail.bulk_order.enums.OrderStatus;
import com.projects.retail.bulk_order.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class OrderMapper {

    private final ProductRepository productRepository;

    public OrderResponseDTO convertEntityToDTO(OrderEntity order){
        return OrderResponseDTO.builder()
                .orderId(order.getOrderId())
                .orderEmail(order.getOrderEmail())
                .orderStatus(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(order.getItems())
                .build();
    }

    public OrderEntity convertDTOToEntity(OrderRequestDTO dto) {

        OrderEntity order = OrderEntity.builder()
                .orderEmail(dto.getOrderEmail())
                .status(OrderStatus.CREATED)
                .totalAmount(dto.getTotalAmount())
                .build();

        List<OrderItemEntity> items = new ArrayList<>();

        for (OrderItemRequestDTO itemDTO : dto.getItems()) {

            ProductEntity product = productRepository.findByProductId(itemDTO.getProductId());

            OrderItemEntity item = OrderItemEntity.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .unitPrice(product.getPrice())
                    .build();

            items.add(item);
        }

        order.setItems(items);

        return order;
    }
}
