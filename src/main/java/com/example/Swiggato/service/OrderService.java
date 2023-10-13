package com.example.Swiggato.service;

import com.example.Swiggato.dto.responseDto.OrderResponse;
import com.example.Swiggato.exception.CustomerNotFoundException;
import com.example.Swiggato.exception.EmptyCartException;
import com.example.Swiggato.model.*;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.DeliveryPartnerRepository;
import com.example.Swiggato.repository.OrderRepository;
import com.example.Swiggato.repository.RestaurantRepository;
import com.example.Swiggato.transformer.OrderTransformer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    final OrderRepository orderRepository;
    final DeliveryPartnerRepository deliveryPartnerRepository;
    final CustomerRepository customerRepository;
    final RestaurantRepository restaurantRepository;
    public OrderService(OrderRepository orderRepository, DeliveryPartnerRepository deliveryPartnerRepository, CustomerRepository customerRepository, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
    }


    public OrderResponse placeOrder(String customerMobile) {

        //check customer existance
        Customer customer = customerRepository.findByPhoneNo(customerMobile);
        if(customer==null){
            throw new CustomerNotFoundException("Invalid Mobile Number!!!");
        }

        // get cart
        Cart cart = customer.getCart();
        // check cart is having items or not
        if(cart.getFoodItems().size()==0){
            throw new EmptyCartException("Sorry!!! Your cart is empty");
        }

        //find deliveryPartner to deliver order
        DeliveryPartner deliveryPartner = deliveryPartnerRepository.findRandomDeliveryPartner();
        Restaurant restaurant = cart.getFoodItems().get(0).getMenuItem().getRestaurant();

        //get list of foods from cart
        List<FoodItem> foodItemList = cart.getFoodItems();

        //prepare order
        OrderEntity orderEntity = OrderTransformer.prepareOrderEntity(cart);
        // save order
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        orderEntity.setCustomer(customer);
        orderEntity.setDeliveryPartner(deliveryPartner);
        orderEntity.setRestaurant(restaurant);
        orderEntity.setFoodItems(foodItemList);


        customer.getOrders().add(savedOrder);  //add order to customer
        deliveryPartner.getOrders().add(savedOrder);  // add order to delivery Partner
        cart.getFoodItems().get(0).getMenuItem().getRestaurant().getOrders().add(savedOrder); // add order to menu


        // set foodItem cart to null

        for(FoodItem foodItem:cart.getFoodItems()){
        foodItem.setCart(null);
         foodItem.setOrder(savedOrder);
        }

        // now clear the cart
         clearCart(cart);

        customerRepository.save(customer); // cart automatically saved
        deliveryPartnerRepository.save(deliveryPartner);
        restaurantRepository.save(restaurant);


        // prepare Order response
        OrderResponse orderResponse =OrderTransformer.prepareOrderResponse(savedOrder);

        return orderResponse;

    }

    // clear the cart
    private void clearCart(Cart cart){
        cart.setCartTotal(0);
        cart.setFoodItems(new ArrayList<>());
    }
}
