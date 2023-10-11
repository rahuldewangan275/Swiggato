package com.example.Swiggato.controller;

import com.example.Swiggato.dto.requestDto.MenuRequest;
import com.example.Swiggato.dto.requestDto.RestaurantRequest;
import com.example.Swiggato.dto.responseDto.MenuResponse;
import com.example.Swiggato.dto.responseDto.RestaurantResponse;
import com.example.Swiggato.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/add-restaurant")
    public ResponseEntity addRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        try {
            RestaurantResponse restaurantResponse = restaurantService.addRestaurant(restaurantRequest);
            return new ResponseEntity(restaurantResponse, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Restaurant already exist",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/status/change")
    public ResponseEntity changeOpenedStatus(@RequestParam("id") int id){
        String msg = restaurantService.changeOpenedStatus(id);
        return new ResponseEntity<>(msg,HttpStatus.ACCEPTED);
    }

    @PostMapping("/add-food-to-restaurant")
    public ResponseEntity addFoodItemToRestaurant(@RequestBody MenuRequest menuRequest, @RequestParam("restaurant-id") int id ){
        try {
            RestaurantResponse restaurantResponse = restaurantService.addMenuItemToRestaurant(menuRequest, id);
            return new ResponseEntity<>(restaurantResponse, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        }

        //get menu of a restaurant
    @GetMapping("/get/menu")
    public ResponseEntity getMenuOfRestaurant(@RequestParam("id") int id){
        try{
            List<MenuResponse> menuResponseList = restaurantService.getMenuOfRestaurant(id);
            return new ResponseEntity<>(menuResponseList,HttpStatus.FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
