package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.responseDto.CartResponse;
import com.example.Swiggato.dto.responseDto.FoodResponse;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.FoodItem;
import lombok.experimental.UtilityClass;

import java.util.List;

public class CartTransformer {

    public static CartResponse CartToCartResponse(Cart cart){
        List<FoodItem> foodItemList = cart.getFoodItems();
        //converting FoodItem to FoodResponse
        List<FoodResponse> foodResponseList = FoodItemTransformer.FoodItemListToFoodResponseList(foodItemList);
        return CartResponse.builder()
                .cartTotal(cart.getCartTotal())
                .foodItems(foodResponseList)
                .build();
    }
}
