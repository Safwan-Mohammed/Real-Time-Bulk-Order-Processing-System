package com.projects.retail.bulk_order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="Product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private long id;

    @UuidGenerator
    @Column(nullable = false, updatable = false, unique = true)
    private UUID productId;

    @Column(nullable = false)
    private String productName;
    private String productDesc;

    @Column(nullable = false)
    private String productCode;

    @Column(nullable = false)
    private long stockQty;

    @Column(nullable = false)
    private double price;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
