package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.requestDto.RestaurantRequest;
import com.example.Swiggato.dto.responseDto.MenuResponse;
import com.example.Swiggato.dto.responseDto.RestaurantResponse;
import com.example.Swiggato.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantTransformer {

    public static Restaurant RestaurantRequestToRestaurant(RestaurantRequest restaurantRequest){
        return Restaurant.builder()
                .name(restaurantRequest.getName())
                .location(restaurantRequest.getLocation())
                .contactNumber(restaurantRequest.getContactNumber())
                .restaurantCategory(restaurantRequest.getRestaurantCategory())
                .availableMenuItems(new ArrayList<>())
                .orders(new ArrayList<>())
                .build();
    }

    public static RestaurantResponse RestaurantToRestaurantResponse(Restaurant restaurant){

        List<MenuResponse> foodItems = MenuItemTransformer.MenuItemListToMenuResponseList(restaurant.getAvailableMenuItems());
        return RestaurantResponse.builder()
                .name(restaurant.getName())
                .location((restaurant.getLocation()))
                .contactNumber(restaurant.getContactNumber())
                .opened(restaurant.isOpened())
                .menu(foodItems)
                .build();
    }
}
