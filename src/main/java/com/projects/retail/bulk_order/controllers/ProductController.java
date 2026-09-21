package com.projects.retail.bulk_order.controllers;

import com.projects.retail.bulk_order.dto.request.product.ProductCreateRequestDTO;
import com.projects.retail.bulk_order.dto.request.product.ProductUpdateRequestDTO;
import com.projects.retail.bulk_order.dto.response.GeneralResponseDTO;
import com.projects.retail.bulk_order.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<GeneralResponseDTO> fetchAllProducts(){
        return productService.fetchAllProducts();
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<GeneralResponseDTO> fetchProduct(@PathVariable("product-id") UUID productId){
        return productService.fetchProduct(productId);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductCreateRequestDTO productCreateRequestDTO){
        return productService.createProduct(productCreateRequestDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateRequestDTO productUpdateRequestDTO){
        return productService.updateProduct(productUpdateRequestDTO);
    }

    @DeleteMapping("/delete/{product-id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID productId){
        return null;
    }
}