package com.projects.retail.bulk_order.entity;

import com.projects.retail.bulk_order.enums.TxnStatus;
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
@Table(name = "Transaction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private long id;

    @Column(nullable = false, updatable = false, unique = true)
    @UuidGenerator
    private UUID txnId;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID orderId;

    @Column(nullable = false)
    private TxnStatus txnStatus;

    @Column(nullable = false)
    private double amount;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
