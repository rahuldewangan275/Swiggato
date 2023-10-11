package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.requestDto.MenuRequest;
import com.example.Swiggato.dto.responseDto.MenuResponse;
import com.example.Swiggato.dto.responseDto.MenuResponse2;
import com.example.Swiggato.model.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class MenuItemTransformer {
    public static MenuItem MenuRequestToMenuItem(MenuRequest menuRequest){
        return MenuItem.builder()
                .dishName(menuRequest.getDishName())
                .price(menuRequest.getPrice())
                .foodCategory(menuRequest.getFoodCategory())
                .veg(menuRequest.isVeg())
                .available(menuRequest.isAvailable())
                .build();
    }
    public static MenuResponse MenuItemToMenuResponse(MenuItem menuItem){
        return MenuResponse.builder()
                .dishName(menuItem.getDishName())
                .price(menuItem.getPrice())
                .foodCategory(menuItem.getFoodCategory())
                .veg(menuItem.isVeg())
                .build();
    }

    // food response with restaurant name
    public static MenuResponse2 MenuItemToMenuResponse2(MenuItem menuItem){
        return MenuResponse2.builder()
                .dishName(menuItem.getDishName())
                .price(menuItem.getPrice())
                .foodCategory(menuItem.getFoodCategory())
                .veg(menuItem.isVeg())
                .restaurantName(menuItem.getRestaurant().getName())
                .build();
    }

    public static List<MenuResponse> MenuItemListToMenuResponseList(List<MenuItem> menuItemList) {
        if(menuItemList == null || menuItemList.size()==0){
          return  new ArrayList<MenuResponse>();
        }
        List<MenuResponse> menuResponseList = new ArrayList<>();
        for (MenuItem menuItem : menuItemList) {
            MenuResponse menuResponse = MenuItemTransformer.MenuItemToMenuResponse(menuItem);
            menuResponseList.add(menuResponse);
        }
        return menuResponseList;
    }

    public static List<MenuResponse2> MenuItemListToMenuResponseList2(List<MenuItem> menuItemList) {
        if(menuItemList == null || menuItemList.size()==0){
            return  new ArrayList<MenuResponse2>();
        }
        List<MenuResponse2> foodResponseList = new ArrayList<>();
        for (MenuItem menuItem : menuItemList) {
            MenuResponse2 foodResponse = MenuItemTransformer.MenuItemToMenuResponse2(menuItem);
            foodResponseList.add(foodResponse);
        }
        return foodResponseList;
    }
}
