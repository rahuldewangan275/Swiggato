package com.example.Swiggato.controller;

import com.example.Swiggato.Enum.FoodCategory;
import com.example.Swiggato.dto.responseDto.FoodResponse;
import com.example.Swiggato.dto.responseDto.FoodResponse2;
import com.example.Swiggato.service.FoodItemService;
import com.example.Swiggato.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodItemController {
    // constructor injection
    final FoodItemService foodItemService;
    final RestaurantService restaurantService;
    @Autowired
    public FoodItemController(FoodItemService foodItemService, RestaurantService restaurantService) {
        this.foodItemService = foodItemService;
        this.restaurantService = restaurantService;
    }



    //get all veg foods of a restaurant
    // get all nonveg foods of a restaurant
    // get cheapest 5 food items of a restaurant

    //// get all foods of a perticular category
    @GetMapping("/get/foods/category/{category}")
    public ResponseEntity getAllFoodsOfParticularCategory(@PathVariable("category") FoodCategory foodCategory){
        List<FoodResponse> foodResponseList = foodItemService.getAllFoodsOfParticularCategory(foodCategory);
        return new ResponseEntity<>(foodResponseList, HttpStatus.ACCEPTED);
    }

    // get all foodcategory items with price above x rupees from a peticular restaurant
    @GetMapping("/get/foods/{category}/{priceAbove}")
    public ResponseEntity getAllFoodsOfParticularCategoryAndPriceAbove(@PathVariable("category") FoodCategory foodCategory,@PathVariable("priceAbove") double priceAbove){
        List<FoodResponse> foodResponseList = foodItemService.getAllFoodsOfParticularCategoryAndPriceAbove(foodCategory,priceAbove);

        if(foodResponseList.size()==0){
            return new ResponseEntity<>("No results Found!!",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foodResponseList, HttpStatus.ACCEPTED);
    }

    // get all nonveg foods
    @GetMapping("get/all-veg")
    public ResponseEntity getAllNonVegFoods(){
        List<FoodResponse> foodResponseList = foodItemService.getAllNonVegFoods();

        if(foodResponseList.size()==0){
            return new ResponseEntity<>("No results Found!!",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foodResponseList, HttpStatus.ACCEPTED);
    }

    // get all veg foods
    @GetMapping("get/all-non-veg")
    public ResponseEntity getAllVegFoods(){
        List<FoodResponse> foodResponseList = foodItemService.getAllVegFoods();

        if(foodResponseList.size()==0){
            return new ResponseEntity<>("No results Found!!",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foodResponseList, HttpStatus.ACCEPTED);
    }

    @GetMapping("get/price-low-to-high")
    public ResponseEntity priceLowToHigh(){
        List<FoodResponse2> foodResponseList = foodItemService.priceLowToHigh();

        if(foodResponseList.size()==0){
            return new ResponseEntity<>("No results Found!!",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foodResponseList, HttpStatus.ACCEPTED);
    }


    //sorted price
    @GetMapping("/get/sorted-restaurant-foods")
    public ResponseEntity sortRestaurantFoodByPrice(@RequestParam("restaurantId") int id){
        String msg="";
        try {
            List<FoodResponse> foodResponseList = foodItemService.sortRestaurantFoodByPrice(id);

            if (foodResponseList.size() == 0) {
                return new ResponseEntity<>("No results Found!!", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>( foodResponseList, HttpStatus.ACCEPTED);
        }catch(Exception e){
          return  new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


}
