package com.projects.retail.bulk_order.repository;

import com.projects.retail.bulk_order.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
