package com.example.Swiggato.service;

import com.example.Swiggato.Enum.FoodCategory;
import com.example.Swiggato.dto.responseDto.MenuResponse;
import com.example.Swiggato.dto.responseDto.MenuResponse2;
import com.example.Swiggato.exception.RestaurantNotFoundException;
import com.example.Swiggato.model.MenuItem;
import com.example.Swiggato.model.Restaurant;
import com.example.Swiggato.repository.MenuItemRepository;
import com.example.Swiggato.repository.RestaurantRepository;
import com.example.Swiggato.transformer.MenuItemTransformer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {
final MenuItemRepository menuItemRepository;
final RestaurantRepository restaurantRepository;
    public MenuItemService(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }



    public List<MenuResponse> getAllFoodsOfParticularCategory(FoodCategory foodCategory) {
        // getting all the food item
        List<MenuItem> menuItemList = menuItemRepository.findAll();

        // convert food-item-list to food-response-list
        List<MenuResponse> menuResponseList = new ArrayList<>();

        for(MenuItem menuItem : menuItemList){
            if(menuItem.getFoodCategory().equals(foodCategory)){
                MenuResponse menuResponse = MenuItemTransformer.MenuItemToMenuResponse(menuItem);
                menuResponseList.add(menuResponse);
            }
        }
        return menuResponseList;
    }


    public List<MenuResponse> getAllFoodsOfParticularCategoryAndPriceAbove(FoodCategory foodCategory, double priceAbove) {
        //getting all required food
        List<MenuItem> menuItemList = menuItemRepository.getAllFoodsOfParticularCategoryAndPriceAbove(foodCategory,priceAbove);

        // convert from food-item-list to food-response-list
        List<MenuResponse> menuResponseList = MenuItemTransformer.MenuItemListToMenuResponseList(menuItemList);
        return menuResponseList;
    }

    public List<MenuResponse> getAllNonVegFoods() {
        //getting all non-veg foods
        List<MenuItem> menuItemList = menuItemRepository.getAllNonVegFoods();
        // convert from food-item-list to food-response-list
        List<MenuResponse> menuResponseList = MenuItemTransformer.MenuItemListToMenuResponseList(menuItemList);
        return menuResponseList;
    }

    public List<MenuResponse> getAllVegFoods() {
        //getting all veg foods
        List<MenuItem> menuItemList = menuItemRepository.getAllVegFoods();
        // convert from food-item-list to food-response-list
        List<MenuResponse> menuResponseList = MenuItemTransformer.MenuItemListToMenuResponseList(menuItemList);
        return menuResponseList;
    }

    public List<MenuResponse2> priceLowToHigh() {
        //getting sorted array wrt price
        List<MenuItem> menuItemList = menuItemRepository.priceLowToHigh();

        // convert from food-item-list to food-response-list
        List<MenuResponse2> foodResponseList = MenuItemTransformer.MenuItemListToMenuResponseList2(menuItemList);
        return foodResponseList;
    }

    public List<MenuResponse> sortRestaurantFoodByPrice(int id) {
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        if(optional.isEmpty()){
            throw new RestaurantNotFoundException("Restaurant doesn't Exist!!");
        }

        //getting sorted food item list of a restaurant with an id
        List<MenuItem> menuItemList = menuItemRepository.sortRestaurantFoodByPrice(id);

        //CONVERT FOOD-ITEM-LIST TO FOOD RESPONSE LIST
        List<MenuResponse> menuResponseList = MenuItemTransformer.MenuItemListToMenuResponseList(menuItemList);
        return menuResponseList;
    }
}
