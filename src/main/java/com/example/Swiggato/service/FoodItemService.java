package com.example.Swiggato.service;

import com.example.Swiggato.Enum.FoodCategory;
import com.example.Swiggato.dto.responseDto.FoodResponse;
import com.example.Swiggato.dto.responseDto.FoodResponse2;
import com.example.Swiggato.exception.RestaurantNotFoundException;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.Restaurant;
import com.example.Swiggato.repository.FoodItemRepository;
import com.example.Swiggato.repository.RestaurantRepository;
import com.example.Swiggato.transformer.FoodItemTransformer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {
final FoodItemRepository foodItemRepository;
final RestaurantRepository restaurantRepository;
    public FoodItemService(FoodItemRepository foodItemRepository, RestaurantRepository restaurantRepository) {
        this.foodItemRepository = foodItemRepository;
        this.restaurantRepository = restaurantRepository;
    }



    public List<FoodResponse> getAllFoodsOfParticularCategory(FoodCategory foodCategory) {
        // getting all the food item
        List<FoodItem> foodItemList = foodItemRepository.findAll();

        // convert food-item-list to food-response-list
        List<FoodResponse> foodResponseList = new ArrayList<>();

        for(FoodItem foodItem : foodItemList){
            if(foodItem.getFoodCategory().equals(foodCategory)){
                FoodResponse foodResponse = FoodItemTransformer.FoodItemToFoodResponse(foodItem);
                foodResponseList.add(foodResponse);
            }
        }
        return foodResponseList;
    }


    public List<FoodResponse> getAllFoodsOfParticularCategoryAndPriceAbove(FoodCategory foodCategory, double priceAbove) {
        //getting all required food
        List<FoodItem> foodItemList = foodItemRepository.getAllFoodsOfParticularCategoryAndPriceAbove(foodCategory,priceAbove);

        // convert from food-item-list to food-response-list
        List<FoodResponse> foodResponseList = FoodItemTransformer.FoodItemListToFoodResponseList(foodItemList);
        return foodResponseList;
    }

    public List<FoodResponse> getAllNonVegFoods() {
        //getting all non-veg foods
        List<FoodItem> foodItemList = foodItemRepository.getAllNonVegFoods();
        // convert from food-item-list to food-response-list
        List<FoodResponse> foodResponseList = FoodItemTransformer.FoodItemListToFoodResponseList(foodItemList);
        return foodResponseList;
    }

    public List<FoodResponse> getAllVegFoods() {
        //getting all veg foods
        List<FoodItem> foodItemList = foodItemRepository.getAllVegFoods();
        // convert from food-item-list to food-response-list
        List<FoodResponse> foodResponseList = FoodItemTransformer.FoodItemListToFoodResponseList(foodItemList);
        return foodResponseList;
    }

    public List<FoodResponse2> priceLowToHigh() {
        //getting sorted array wrt price
        List<FoodItem> foodItemList = foodItemRepository.priceLowToHigh();

        // convert from food-item-list to food-response-list
        List<FoodResponse2> foodResponseList = FoodItemTransformer.FoodItemListToFoodResponseList2(foodItemList);
        return foodResponseList;
    }

    public List<FoodResponse> sortRestaurantFoodByPrice(int id) {
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        if(optional.isEmpty()){
            throw new RestaurantNotFoundException("Restaurant doesn't Exist!!");
        }

        //getting sorted food item list of a restaurant with an id
        List<FoodItem>foodItemList = foodItemRepository.sortRestaurantFoodByPrice(id);

        //CONVERT FOOD-ITEM-LIST TO FOOD RESPONSE LIST
        List<FoodResponse> foodResponseList = FoodItemTransformer.FoodItemListToFoodResponseList(foodItemList);
        return  foodResponseList;
    }
}
