package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.responseDto.FoodResponse;
import com.example.Swiggato.dto.responseDto.OrderResponse;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.OrderEntity;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderTransformer {

    public static OrderEntity prepareOrderEntity(Cart cart){
        return OrderEntity.builder()
                .orderId(String.valueOf(UUID.randomUUID()))
                .orderTotal(cart.getCartTotal())
                .build();
    }

    public static OrderResponse prepareOrderResponse(OrderEntity savedOrder) {

        List<FoodItem> foodItems = savedOrder.getFoodItems();
        List<FoodResponse> foodResponseList = new ArrayList<>();

        for(FoodItem foodItem : foodItems){
            FoodResponse foodResponse = FoodItemTransformer.FoodItemToFoodResponse(foodItem);
            foodResponseList.add(foodResponse);
        }

        return OrderResponse.builder()
                .orderId(savedOrder.getOrderId())
                .orderTotal(savedOrder.getOrderTotal())
                .orderTime(savedOrder.getOrderTime())

                .customerName(savedOrder.getCustomer().getName())
                .customerMobile(savedOrder.getCustomer().getPhoneNo())

                .deliveryPartnerName(savedOrder.getDeliveryPartner().getName())
                .deliveryPartnerMobile(savedOrder.getDeliveryPartner().getMobileNo())
                .restaurantName(savedOrder.getRestaurant().getName())
                .foodResponseList(foodResponseList)
                .build();
    }
}
