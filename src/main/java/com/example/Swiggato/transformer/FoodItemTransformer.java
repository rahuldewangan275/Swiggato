package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.responseDto.FoodResponse;
import com.example.Swiggato.model.FoodItem;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;


public class FoodItemTransformer {
    public static FoodResponse FoodItemToFoodResponse(FoodItem foodItem){
        return FoodResponse.builder()
                .dishName(foodItem.getDishName())
                .price(foodItem.getPrice())
                .foodCategory(foodItem.getFoodCategory())
                .veg(foodItem.isVeg())
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
}
