package com.projects.retail.bulk_order.controllers;

import com.projects.retail.bulk_order.dto.request.order.OrderRequestDTO;
import com.projects.retail.bulk_order.dto.response.GeneralResponseDTO;
import com.projects.retail.bulk_order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.createOrder(orderRequestDTO);
    }

    @GetMapping("/fetchAllOrders")
    public ResponseEntity<GeneralResponseDTO> fetchAllOrders(){
        return orderService.fetchAllOrders();
    }

    @GetMapping("/fetchOrder/{orderId}")
    public ResponseEntity<GeneralResponseDTO> fetchOrder(@PathVariable UUID orderId){
        return orderService.fetchOrder(orderId);
    }

}
