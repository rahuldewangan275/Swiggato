package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.responseDto.CartResponse;
import com.example.Swiggato.dto.responseDto.FoodResponse;
import com.example.Swiggato.dto.responseDto.MenuResponse;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartTransformer {

    public static CartResponse CartToCartResponse(Cart cart){

        List<FoodResponse> foodResponseList = new ArrayList<>();
        for(FoodItem foodItem : cart.getFoodItems()){
            FoodResponse foodResponse = FoodItemTransformer.FoodItemToFoodResponse(foodItem);
            foodResponseList.add(foodResponse);
        }


        return CartResponse.builder()
                .cartTotal(cart.getCartTotal())
                .foodList(foodResponseList)
                .build();

    }
}
