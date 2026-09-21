package com.projects.retail.bulk_order.mapper;

import com.projects.retail.bulk_order.dto.request.product.ProductCreateRequestDTO;
import com.projects.retail.bulk_order.dto.request.product.ProductUpdateRequestDTO;
import com.projects.retail.bulk_order.dto.response.product.ProductResponseDTO;
import com.projects.retail.bulk_order.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapper {

    public ProductResponseDTO convertEntityToDTO(ProductEntity product){
        return ProductResponseDTO.builder()
                .productId(product.getProductId())
                .productDesc(product.getProductDesc())
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .price(product.getPrice())
                .stockQty(product.getStockQty())
                .build();
    }

    public ProductEntity convertDTOToEntity(ProductCreateRequestDTO dto){
        return ProductEntity.builder()
                .productCode(dto.getProductCode())
                .productDesc(dto.getProductDesc())
                .productName(dto.getProductName())
                .price(dto.getPrice())
                .stockQty(dto.getStockQty())
                .build();
    }

    public ProductEntity convertDTOToEntity(ProductUpdateRequestDTO dto){
        return ProductEntity.builder()
                .productCode(dto.getProductCode())
                .productDesc(dto.getProductDesc())
                .productName(dto.getProductName())
                .price(dto.getPrice())
                .stockQty(dto.getStockQty())
                .build();
    }
}