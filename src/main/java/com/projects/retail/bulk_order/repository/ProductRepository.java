package com.projects.retail.bulk_order.repository;

import com.projects.retail.bulk_order.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByProductId(UUID productId);
    ProductEntity findByProductCode(String productCode);
}
