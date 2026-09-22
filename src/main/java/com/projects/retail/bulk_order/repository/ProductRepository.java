package com.projects.retail.bulk_order.repository;

import com.projects.retail.bulk_order.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByProductId(UUID productId);
    ProductEntity findByProductCode(String productCode);

    @Modifying
    @Query(""" 
            UPDATE ProductEntity p 
            SET p.stockQty = p.stockQty - :quantity 
            WHERE p.id = :productId 
            AND p.stockQty >= :quantity 
            """)
    int decreaseStock(@Param("productId") long productId, @Param("quantity") long quantity );
}
