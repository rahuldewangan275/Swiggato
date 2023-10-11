package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.requestDto.CustomerRequest;
import com.example.Swiggato.dto.responseDto.CartResponse;
import com.example.Swiggato.dto.responseDto.CustomerResponse;
import com.example.Swiggato.model.Customer;

import java.util.ArrayList;


public class CustomerTransformer {

    public static Customer CustomerRequestToCustomer(CustomerRequest customerRequest){
        return Customer.builder()
                .name(customerRequest.getName())
                .email(customerRequest.getEmail())
                .phoneNo(customerRequest.getPhoneNo())
                .address(customerRequest.getAddress())
                .gender(customerRequest.getGender())
                .orders(new ArrayList<>()) // @@@@@
                .build();

    }

    public static CustomerResponse CustomerToCustomerResponse(Customer customer){
        CartResponse cartResponse = CartTransformer.CartToCartResponse(customer.getCart());
        //  cart to cart response

        //adding cartResponse to customerResponse
        return CustomerResponse.builder()
                .name(customer.getName())
                .phoneNo(customer.getPhoneNo())
                .address(customer.getAddress())
                .cartResponse(cartResponse)
                .build();
    }
}
