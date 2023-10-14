package com.example.Swiggato.service;

import com.example.Swiggato.dto.requestDto.FoodRequest;
import com.example.Swiggato.dto.responseDto.FoodResponse;
import com.example.Swiggato.exception.CustomerNotFoundException;
import com.example.Swiggato.exception.MenuItemNotFoundexception;
import com.example.Swiggato.exception.RestaurantIsClosed;
import com.example.Swiggato.model.*;
import com.example.Swiggato.repository.CartRepository;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.FoodItemRepository;
import com.example.Swiggato.repository.MenuItemRepository;
import com.example.Swiggato.transformer.FoodItemTransformer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    final CustomerRepository customerRepository;
    final MenuItemRepository menuRepository;
    final FoodItemRepository foodItemRepository;
    final CartRepository cartRepository;
    public CartService(CustomerRepository customerRepository, MenuItemRepository menuRepository, FoodItemRepository foodItemRepository, CartRepository cartRepository) {
        this.customerRepository = customerRepository;
        this.menuRepository = menuRepository;
        this.foodItemRepository = foodItemRepository;
        this.cartRepository = cartRepository;
    }

    public FoodResponse addFoodItemToCart(FoodRequest foodRequest) {

        //verify customer
        String customerMobileNo = foodRequest.getCustomerMobile();
        Customer customer = customerRepository.findByPhoneNo(customerMobileNo);
        if(customer==null){
            throw new CustomerNotFoundException("Customer doesn't Exist");
        }

        //verify restaurant
        Optional<MenuItem> menuItemOptional =  menuRepository.findById(foodRequest.getMenuItemId());
        if(menuItemOptional.isEmpty()){
            throw new MenuItemNotFoundexception("Menu Item Not Present in Restaurant");
        }

        //menu item
        MenuItem menuItem = menuItemOptional.get();
        int restaurantId = menuItem.getRestaurant().getId();



        //check item availability
         if(!menuItem.isAvailable()){
             throw new MenuItemNotFoundexception("Given Dish Is Out Of Stock For Now!!");
         }

         //check for restaurant is open or closed
        if(!menuItem.getRestaurant().isOpened()){
            throw new RestaurantIsClosed("Restaurant Is Closed");
        }

        //getting cart from customer
        Cart cart = customer.getCart();

        // having item from same restaurant
        if(cart.getFoodItems().size()!=0){
            Restaurant currRestaurant = cart.getFoodItems().get(0).getMenuItem().getRestaurant();
            Restaurant newRestaurant = menuItem.getRestaurant();

            if(!currRestaurant.equals(newRestaurant)){
                List<FoodItem> foodItems = cart.getFoodItems();
                for(FoodItem foodItem: foodItems) {
                    foodItem.setCart(null);
                    foodItem.setOrder(null);
                    foodItem.setMenuItem(null);
                }
                cart.setCartTotal(0);
                cart.getFoodItems().clear();
                foodItemRepository.deleteAll(foodItems);
            }
        }

        boolean alreadyExists = false;
        FoodItem savedFoodItem = null;
        if(cart.getFoodItems().size()!=0){
            for(FoodItem foodItem: cart.getFoodItems()){
                if(foodItem.getMenuItem().getId()==menuItem.getId()){
                    savedFoodItem = foodItem;
                    int curr = foodItem.getRequiredQuantity();
                    foodItem.setRequiredQuantity(curr+foodRequest.getRequiredQuantity());
                    alreadyExists = true;
                    break;
                }
            }
        }

        if(!alreadyExists){
            FoodItem foodItem = FoodItem.builder()
                    .menuItem(menuItem)
                    .requiredQuantity(foodRequest.getRequiredQuantity())
                    .totalCost(foodRequest.getRequiredQuantity()*menuItem.getPrice())
                    .build();

            savedFoodItem = foodItemRepository.save(foodItem);
            cart.getFoodItems().add(savedFoodItem);
            menuItem.getFoodItemList().add(savedFoodItem);
            savedFoodItem.setCart(cart);
        }

        double cartTotal = 0;
        for(FoodItem food: cart.getFoodItems()){
            cartTotal += food.getRequiredQuantity()*food.getMenuItem().getPrice();
        }

        cart.setCartTotal(cartTotal);
        // save
        Cart savedCart = cartRepository.save(cart);
        MenuItem savedMenuItem = menuRepository.save(menuItem);

        // prepare
        List<FoodResponse> foodResponseList = new ArrayList<>();
        for(FoodItem food: cart.getFoodItems()){
            foodResponseList.add(FoodItemTransformer.FoodItemToFoodResponse(food));
        }

                 //convert food-Item to Food Response
               FoodResponse foodResponse = FoodItemTransformer.FoodItemToFoodResponse(savedFoodItem);
               return foodResponse;

    }

    private void clearCart(Cart cart){
        cart.setCartTotal(0);
        cart.setFoodItems(new ArrayList<>());
    }

}
