package com.projects.retail.bulk_order.entity;

import com.projects.retail.bulk_order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private long id;

    @UuidGenerator
    @Column(nullable = false, updatable = false, unique = true)
    private UUID orderId;

    @Column(nullable = false)
    private String orderEmail;

    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items;
}
