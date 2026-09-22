package com.projects.retail.bulk_order.service;

import com.projects.retail.bulk_order.entity.OrderEntity;
import com.projects.retail.bulk_order.entity.OrderItemEntity;
import com.projects.retail.bulk_order.exception.OutOfStockException;
import com.projects.retail.bulk_order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;

    @Transactional
    public void reserveStock(OrderEntity order) {

        for (OrderItemEntity item : order.getItems()) {
            int updatedRows = productRepository.decreaseStock(item.getProduct().getId(),item.getQuantity());
            if (updatedRows == 0)
                throw new OutOfStockException("Insufficient stock for product: " + item.getProduct().getProductId());
        }
    }
}
