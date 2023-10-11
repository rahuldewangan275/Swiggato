package com.example.Swiggato.service;

import com.example.Swiggato.dto.requestDto.MenuRequest;
import com.example.Swiggato.dto.requestDto.RestaurantRequest;
import com.example.Swiggato.dto.responseDto.MenuResponse;
import com.example.Swiggato.dto.responseDto.RestaurantResponse;
import com.example.Swiggato.exception.RestaurantNotFoundException;
import com.example.Swiggato.model.MenuItem;
import com.example.Swiggato.model.Restaurant;
import com.example.Swiggato.repository.RestaurantRepository;
import com.example.Swiggato.transformer.MenuItemTransformer;
import com.example.Swiggato.transformer.RestaurantTransformer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    /*
    Constructor Injection not field injection
     */
    final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }


    public RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest) {
        // dto -> model
        Restaurant restaurant = RestaurantTransformer.RestaurantRequestToRestaurant(restaurantRequest);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        RestaurantResponse restaurantResponse = RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);

        return restaurantResponse;

    }

    public String changeOpenedStatus(int id) {

        Optional<Restaurant> optional = restaurantRepository.findById(id);
        if(optional.isEmpty()){
            throw  new RestaurantNotFoundException("Restaurant doesn't exist");
        }

        Restaurant restaurant = optional.get();
        restaurant.setOpened(!restaurant.isOpened());
       Restaurant savedRestaurant =  restaurantRepository.save(restaurant);

        if(savedRestaurant.isOpened()){
            return "Restaurant is Opened Now";
        }
        return "Restaurant is Closed";
    }

    public RestaurantResponse addMenuItemToRestaurant(MenuRequest menuRequest, int id) {
        MenuItem menuItem = MenuItemTransformer.MenuRequestToMenuItem(menuRequest);
        // check restaurant valid or not
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        if(optional.isEmpty()){
            throw new RestaurantNotFoundException("!!Restaurant doesn't exist!!");
        }

        //get restaurant
        Restaurant restaurant = optional.get();
        menuItem.setRestaurant(restaurant);

        // add food item to restaurant
        restaurant.getAvailableMenuItems().add(menuItem);

        // saved both restaurant and food Item
        Restaurant saved = restaurantRepository.save(restaurant);
        return RestaurantTransformer.RestaurantToRestaurantResponse(saved);
    }

    public List<MenuResponse> getMenuOfRestaurant(int id) {
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        if(optional.isEmpty()){
            throw new RestaurantNotFoundException("Restaurant doesn't Exist");
        }

        Restaurant restaurant = optional.get();
        List<MenuItem> menuItemList = restaurant.getAvailableMenuItems();

        List<MenuResponse> menuResponseList = MenuItemTransformer.MenuItemListToMenuResponseList(menuItemList);

        return menuResponseList;

    }


}
