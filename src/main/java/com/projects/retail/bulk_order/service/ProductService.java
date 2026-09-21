package com.projects.retail.bulk_order.service;

import com.projects.retail.bulk_order.dto.request.product.ProductCreateRequestDTO;
import com.projects.retail.bulk_order.dto.request.product.ProductUpdateRequestDTO;
import com.projects.retail.bulk_order.dto.response.GeneralResponseDTO;
import com.projects.retail.bulk_order.dto.response.product.ProductResponseDTO;
import com.projects.retail.bulk_order.entity.ProductEntity;
import com.projects.retail.bulk_order.mapper.ProductMapper;
import com.projects.retail.bulk_order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private <T> ResponseEntity<T> returnResponseEntity(HttpStatus status, T message){
        return ResponseEntity.status(status).body(message);
    }

    public ResponseEntity<GeneralResponseDTO> fetchAllProducts(){
        try{
            List<ProductEntity> productEntityList = productRepository.findAll();
            List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
            for(ProductEntity product : productEntityList)
                productResponseDTOS.add(productMapper.convertEntityToDTO(product));
            return returnResponseEntity(HttpStatus.OK, GeneralResponseDTO.builder().message("Fetch Successful").data(productResponseDTOS).build());
        } catch (Exception e) {
            return returnResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, GeneralResponseDTO.builder().message("Error Occurred : " + e.getMessage()).build());
        }
    }

    public ResponseEntity<GeneralResponseDTO> fetchProduct(UUID productId){
        try{
            ProductEntity product = productRepository.findByProductId(productId);
            if(product == null)
                return returnResponseEntity(HttpStatus.NOT_FOUND, GeneralResponseDTO.builder().message("Product Not Found").build());
            ProductResponseDTO productResponseDTO = productMapper.convertEntityToDTO(product);
            return returnResponseEntity(HttpStatus.OK, GeneralResponseDTO.builder().message("Success").data(productResponseDTO).build());
        } catch (Exception e) {
            return returnResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, GeneralResponseDTO.builder().message("Error Occurred : " + e.getMessage()).build());
        }
    }

    public ResponseEntity<GeneralResponseDTO> createProduct(ProductCreateRequestDTO dto){
        try{
            ProductEntity product = productRepository.findByProductCode(dto.getProductCode());
            if(product != null)
                return returnResponseEntity(HttpStatus.BAD_REQUEST, GeneralResponseDTO.builder().message("Product Code already exists").build());
            product = productMapper.convertDTOToEntity(dto);
            productRepository.save(product);
            return returnResponseEntity(HttpStatus.CREATED, GeneralResponseDTO.builder().message("Creation Success").data(productMapper.convertEntityToDTO(product)).build());
        } catch (Exception e) {
            return returnResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, GeneralResponseDTO.builder().message("Error Occurred : "+e.getMessage()).build());
        }
    }

    public ResponseEntity<GeneralResponseDTO> updateProduct(ProductUpdateRequestDTO dto){
        try{
            ProductEntity product = productRepository.findByProductId(dto.getProductId());
            if(product == null)
                return returnResponseEntity(HttpStatus.NOT_FOUND, GeneralResponseDTO.builder().message("Product doesnt exist").build());
            product = productMapper.convertDTOToEntity(dto);
            productRepository.save(product);
            return returnResponseEntity(HttpStatus.OK, GeneralResponseDTO.builder().message("Updation Success").data(productMapper.convertEntityToDTO(product)).build());
        } catch (Exception e) {
            return returnResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, GeneralResponseDTO.builder().message("Error Occurred : "+e.getMessage()).build());
        }
    }

    public ResponseEntity<GeneralResponseDTO> deleteProduct(UUID productId){
        try{
            ProductEntity product = productRepository.findByProductId(productId);
            if(product == null)
                return returnResponseEntity(HttpStatus.NOT_FOUND, GeneralResponseDTO.builder().message("Product doesnt exist").build());
            productRepository.deleteById(product.getId());
            return returnResponseEntity(HttpStatus.OK, GeneralResponseDTO.builder().message("Deletion Success").build());
        } catch (Exception e) {
            return returnResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, GeneralResponseDTO.builder().message("Error Occurred : "+e.getMessage()).build());
        }
    }
}