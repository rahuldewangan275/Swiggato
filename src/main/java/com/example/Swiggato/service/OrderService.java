package com.example.Swiggato.service;

import com.example.Swiggato.dto.responseDto.FoodResponse;
import com.example.Swiggato.dto.responseDto.OrderResponse;
import com.example.Swiggato.exception.CustomerNotFoundException;
import com.example.Swiggato.exception.EmptyCartException;
import com.example.Swiggato.model.*;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.DeliveryPartnerRepository;
import com.example.Swiggato.repository.OrderRepository;
import com.example.Swiggato.repository.RestaurantRepository;
import com.example.Swiggato.transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    JavaMailSender javaMailSender;  // to send an email
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

        //prepare an email of confirmation
        String customerName = customer.getName();
        String customerMo = customer.getPhoneNo();
        String customerAddress = customer.getAddress();
        String restaurantName = orderResponse.getRestaurantName();
        String DeliveryBoy = orderResponse.getDeliveryPartnerName();
        String DeliveryBoyMo=orderResponse.getDeliveryPartnerMobile();
        String orderId = orderEntity.getOrderId();


        String foodItemString = "";
        for(FoodResponse foodResponse : orderResponse.getFoodResponseList()){
            foodItemString += foodResponse.getDishName()+" :₹"+foodResponse.getPrice()+" \n ";
        }

        String msgForCustomer1 = "Dear,"+customerName+"\n"+"We're thrilled to inform you that your food delivery order has been successfully placed and is on its way to you. Your order details are as follows:\n" +
                "OrderId :#"+orderId +"\n"
                +"Delivery Address :"+customerAddress+"\n"+
                "Contact Number :"+customerMo+"\n"+"\n"+
                "ORDER DETAILS:"
                +"\n"+
                "Restaurant :"+restaurantName+"\n"+
                "Delivery Partner :"+DeliveryBoy +"\n"+
                "Delivery Partner Contact :"+DeliveryBoyMo +"\n"+
                "Order Date :"+orderResponse.getOrderTime()+"\n"+"\n"+

                "ORDER SUMMARY :"
                +"\n"+foodItemString+"\n"+
                        "Order Total :₹"+orderResponse.getOrderTotal()+"\n \n \n"+
                        "              THANK YOU         ";


                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
             simpleMailMessage.setFrom("rahulkumardewangan380@gmail.com");
             simpleMailMessage.setTo(customer.getEmail());
                simpleMailMessage.setSubject("SWIGGATO ORDER PLACED");
                simpleMailMessage.setText(msgForCustomer1);
                javaMailSender.send(simpleMailMessage);
                return orderResponse;

    }

    // clear the cart
    private void clearCart(Cart cart){
        cart.setCartTotal(0);
        cart.setFoodItems(new ArrayList<>());
    }
}
