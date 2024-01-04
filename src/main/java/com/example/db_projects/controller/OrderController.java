// src/main/java/com/yourpackage/controller/OrderController.java
package com.example.db_projects.controller;

import com.example.db_projects.dto.OrderDto;
import com.example.db_projects.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
        try {
            orderService.createOrder(orderDto);
            return ResponseEntity.ok(Collections.singletonMap("message", "Sipariş başarıyla oluşturuldu"));
        } catch (Exception e) {
            logger.error("Sipariş oluşturma hatası: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Sipariş oluşturulamadı"));
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        try {
            List<OrderDto> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Siparişleri getirme hatası: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @PostMapping("/update/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable int orderId) {
        try {
            orderService.updateOrderStatus(orderId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Sipariş durumu güncellenirken hata oluştu: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/delivered")
    public ResponseEntity<List<OrderDto>> getDeliveredOrders() {
        try {
            List<OrderDto> deliveredOrders = orderService.getDeliveredOrders();
            return ResponseEntity.ok(deliveredOrders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}
