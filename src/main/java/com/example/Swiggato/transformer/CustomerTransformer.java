package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.requestDto.CustomerRequest;
import com.example.Swiggato.dto.responseDto.CartResponse;
import com.example.Swiggato.dto.responseDto.CustomerResponse;
import com.example.Swiggato.model.Customer;
import com.example.Swiggato.model.FoodItem;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;


public class CustomerTransformer {

    public static Customer CustomerRequestToCustomer(CustomerRequest customerRequest){
        return Customer.builder()
                .name(customerRequest.getName())
                .email(customerRequest.getEmail())
                .phoneNo(customerRequest.getPhoneNo())
                .address(customerRequest.getAddress())
                .gender(customerRequest.getGender())
                .build();

    }

    public static CustomerResponse CustomerToCustomerResponse(Customer customer){
        //  cart to cart response
        CartResponse cartResponse = CartTransformer.CartToCartResponse(customer.getCart());

        //adding cartResponse to customerResponse
        return CustomerResponse.builder()
                .name(customer.getName())
                .phoneNo(customer.getPhoneNo())
                .address(customer.getAddress())
                .cartResponse(cartResponse)
                .build();
    }
}
