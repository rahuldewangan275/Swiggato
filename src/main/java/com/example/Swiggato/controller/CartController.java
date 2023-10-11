package com.example.Swiggato.controller;

import com.example.Swiggato.dto.requestDto.FoodRequest;
import com.example.Swiggato.dto.responseDto.FoodResponse;
import com.example.Swiggato.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add/foodItem")
    public ResponseEntity addFoodItemToCart(@RequestBody FoodRequest foodRequest){
try{
    FoodResponse foodResponse = cartService.addFoodItemToCart(foodRequest);
    return new ResponseEntity<>(foodResponse, HttpStatus.CREATED);
}
catch(Exception e){
    return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
}
    }
}
