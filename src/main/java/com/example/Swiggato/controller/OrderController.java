package com.example.Swiggato.controller;

import com.example.Swiggato.dto.responseDto.OrderResponse;
import com.example.Swiggato.model.Customer;
import com.example.Swiggato.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

@PostMapping("/place/mobile/{mobile}")
    public ResponseEntity placeOrder(@PathVariable("mobile")  String customerMobile){
        try {
            OrderResponse orderResponse = orderService.placeOrder(customerMobile);
            return new ResponseEntity(orderResponse, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        }
}
