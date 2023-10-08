package com.example.Swiggato.service;

import com.example.Swiggato.dto.requestDto.CustomerRequest;
import com.example.Swiggato.dto.responseDto.CustomerResponse;
import com.example.Swiggato.exception.CustomerNotFoundException;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.Customer;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
@Autowired
CustomerRepository customerRepository;
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        //dto -> model
        Customer customer = CustomerTransformer.CustomerRequestToCustomer(customerRequest);

        //allocate a cart
        Cart cart = Cart.builder()
                .cartTotal(0)
                .customer(customer)
                .build();
        customer.setCart(cart);

        Customer savedCustomer = customerRepository.save(customer); // saved both customer and cart

        //prepare response dto
        return CustomerTransformer.CustomerToCustomerResponse(savedCustomer);
    }

    public CustomerResponse getCustomerByMobile(String mobile) { // phone no or mobile is same thing
       Customer customer = customerRepository.findByPhoneNo(mobile);
if(customer==null){
    throw new CustomerNotFoundException("Customer Not Found!!!!\nInvalid Mobile Number");
}
return CustomerTransformer.CustomerToCustomerResponse(customer);
    }
}
