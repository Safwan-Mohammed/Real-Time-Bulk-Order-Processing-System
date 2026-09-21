package com.projects.retail.bulk_order.repository;

import com.projects.retail.bulk_order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByOrderId(UUID orderId);
}
