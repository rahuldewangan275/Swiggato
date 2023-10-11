package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.responseDto.FoodResponse;
import com.example.Swiggato.model.FoodItem;

public class FoodItemTransformer {

    public static FoodResponse FoodItemToFoodResponse(FoodItem foodItem){

        return FoodResponse.builder()
                .dishName(foodItem.getMenuItem().getDishName())
                .price(foodItem.getMenuItem().getPrice())
                .category(foodItem.getMenuItem().getFoodCategory())
                .veg(foodItem.getMenuItem().isVeg())
                .quantityAdded(foodItem.getRequiredQuantity())
                .build();

    }
}
