package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.requestDto.FoodRequest;
import com.example.Swiggato.dto.responseDto.FoodResponse;
import com.example.Swiggato.dto.responseDto.FoodResponse2;
import com.example.Swiggato.model.FoodItem;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;


public class FoodItemTransformer {
    public static FoodItem FoodRequestToFoodItem(FoodRequest foodRequest){
        return FoodItem.builder()
                .dishName(foodRequest.getDishName())
                .price(foodRequest.getPrice())
                .foodCategory(foodRequest.getFoodCategory())
                .veg(foodRequest.isVeg())
                .available(foodRequest.isAvailable())
                .build();
    }
    public static FoodResponse FoodItemToFoodResponse(FoodItem foodItem){
        return FoodResponse.builder()
                .dishName(foodItem.getDishName())
                .price(foodItem.getPrice())
                .foodCategory(foodItem.getFoodCategory())
                .veg(foodItem.isVeg())
                .build();
    }

    // food response with restaurant name
    public static FoodResponse2 FoodItemToFoodResponse2(FoodItem foodItem){
        return FoodResponse2.builder()
                .dishName(foodItem.getDishName())
                .price(foodItem.getPrice())
                .foodCategory(foodItem.getFoodCategory())
                .veg(foodItem.isVeg())
                .restaurantName(foodItem.getRestaurant().getName())
                .build();
    }

    public static List<FoodResponse> FoodItemListToFoodResponseList(List<FoodItem>foodItemList) {
        if(foodItemList == null || foodItemList.size()==0){
          return  new ArrayList<FoodResponse>();
        }
        List<FoodResponse> foodResponseList = new ArrayList<>();
        for (FoodItem foodItem : foodItemList) {
            FoodResponse foodResponse = FoodItemTransformer.FoodItemToFoodResponse(foodItem);
            foodResponseList.add(foodResponse);
        }
        return foodResponseList;
    }

    public static List<FoodResponse2> FoodItemListToFoodResponseList2(List<FoodItem>foodItemList) {
        if(foodItemList == null || foodItemList.size()==0){
            return  new ArrayList<FoodResponse2>();
        }
        List<FoodResponse2> foodResponseList = new ArrayList<>();
        for (FoodItem foodItem : foodItemList) {
            FoodResponse2 foodResponse = FoodItemTransformer.FoodItemToFoodResponse2(foodItem);
            foodResponseList.add(foodResponse);
        }
        return foodResponseList;
    }
}
