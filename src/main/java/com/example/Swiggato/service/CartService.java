package com.example.Swiggato.service;

import com.example.Swiggato.dto.requestDto.FoodRequest;
import com.example.Swiggato.dto.responseDto.FoodResponse;
import com.example.Swiggato.exception.CustomerNotFoundException;
import com.example.Swiggato.exception.MenuItemNotFoundexception;
import com.example.Swiggato.exception.RestaurantIsClosed;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.Customer;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.MenuItem;
import com.example.Swiggato.repository.CartRepository;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.FoodItemRepository;
import com.example.Swiggato.repository.MenuItemRepository;
import com.example.Swiggato.transformer.FoodItemTransformer;
import org.springframework.stereotype.Service;

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

        double totalFoodCost = menuItem.getPrice()* foodRequest.getRequiredQuantity();
        double updatedCartTotal = cart.getCartTotal()+totalFoodCost;

        //set new cart total
        cart.setCartTotal(updatedCartTotal);

       //creating food-item
        FoodItem foodItem = FoodItem.builder()
                .requiredQuantity(foodRequest.getRequiredQuantity())
                .totalCost(totalFoodCost)
                .cart(cart)
                .menuItem(menuItem)
                .build();

                 FoodItem savedFoodItem = foodItemRepository.save(foodItem);   // saved first to avoid duplicate entry in database

                 cart.getFoodItems().add(savedFoodItem); // adding food to cart
                 menuItem.getFoodItemList().add(savedFoodItem); // saving food-item to menuItem

                  Cart savedCart = cartRepository.save(cart);
                  MenuItem savedMenuItem = menuRepository.save(menuItem);


                 //convert food-Item to Food Response
               FoodResponse foodResponse = FoodItemTransformer.FoodItemToFoodResponse(savedFoodItem);
               return foodResponse;

    }
}
