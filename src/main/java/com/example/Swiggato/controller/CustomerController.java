package com.example.Swiggato.controller;

import com.example.Swiggato.dto.requestDto.CustomerRequest;
import com.example.Swiggato.dto.responseDto.CustomerResponse;
import com.example.Swiggato.exception.CustomerNotFoundException;
import com.example.Swiggato.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/add-customer")
    public ResponseEntity addCustomer(@RequestBody CustomerRequest customerRequest){
    CustomerResponse customerResponse = customerService.addCustomer(customerRequest);
    return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get-customer-by-mobile/{mobile}")
    public ResponseEntity getCustomerByMobile(@PathVariable("mobile") String mobile){
        try {
            CustomerResponse customerResponse = customerService.getCustomerByMobile(mobile);
            return new ResponseEntity<>(customerResponse, HttpStatus.FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
