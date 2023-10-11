package com.example.Swiggato.controller;

import com.example.Swiggato.Enum.FoodCategory;
import com.example.Swiggato.dto.responseDto.MenuResponse;
import com.example.Swiggato.dto.responseDto.MenuResponse2;
import com.example.Swiggato.service.MenuItemService;
import com.example.Swiggato.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class MenuController {
    // constructor injection
    final MenuItemService menuItemService;
    final RestaurantService restaurantService;
    @Autowired
    public MenuController(MenuItemService menuItemService, RestaurantService restaurantService) {
        this.menuItemService = menuItemService;
        this.restaurantService = restaurantService;
    }

    //// get all foods of a perticular category
    @GetMapping("/get/foods/category/{category}")
    public ResponseEntity getAllFoodsOfParticularCategory(@PathVariable("category") FoodCategory foodCategory){
        List<MenuResponse> menuResponseList = menuItemService.getAllFoodsOfParticularCategory(foodCategory);
        return new ResponseEntity<>(menuResponseList, HttpStatus.ACCEPTED);
    }

    // get all foodcategory items with price above x rupees from a peticular restaurant
    @GetMapping("/get/foods/{category}/{priceAbove}")
    public ResponseEntity getAllFoodsOfParticularCategoryAndPriceAbove(@PathVariable("category") FoodCategory foodCategory,@PathVariable("priceAbove") double priceAbove){
        List<MenuResponse> menuResponseList = menuItemService.getAllFoodsOfParticularCategoryAndPriceAbove(foodCategory,priceAbove);

        if(menuResponseList.size()==0){
            return new ResponseEntity<>("No results Found!!",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menuResponseList, HttpStatus.ACCEPTED);
    }

    // get all nonveg foods
    @GetMapping("get/all-veg")
    public ResponseEntity getAllNonVegFoods(){
        List<MenuResponse> menuResponseList = menuItemService.getAllNonVegFoods();

        if(menuResponseList.size()==0){
            return new ResponseEntity<>("No results Found!!",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menuResponseList, HttpStatus.ACCEPTED);
    }

    // get all veg foods
    @GetMapping("get/all-non-veg")
    public ResponseEntity getAllVegFoods(){
        List<MenuResponse> menuResponseList = menuItemService.getAllVegFoods();

        if(menuResponseList.size()==0){
            return new ResponseEntity<>("No results Found!!",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menuResponseList, HttpStatus.ACCEPTED);
    }

    @GetMapping("get/price-low-to-high")
    public ResponseEntity priceLowToHigh(){
        List<MenuResponse2> foodResponseList = menuItemService.priceLowToHigh();

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
            List<MenuResponse> menuResponseList = menuItemService.sortRestaurantFoodByPrice(id);

            if (menuResponseList.size() == 0) {
                return new ResponseEntity<>("No results Found!!", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(menuResponseList, HttpStatus.ACCEPTED);
        }catch(Exception e){
          return  new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


}
