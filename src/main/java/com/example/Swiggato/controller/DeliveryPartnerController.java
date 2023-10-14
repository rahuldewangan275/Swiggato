package com.example.Swiggato.controller;

import com.example.Swiggato.dto.requestDto.DeliveryPartnerRequest;
import com.example.Swiggato.dto.responseDto.DeliveryPartnerResponse;
import com.example.Swiggato.service.DeliveryPartnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("delivery-partner")
public class DeliveryPartnerController {
    final DeliveryPartnerService deliveryPartnerService;

    public DeliveryPartnerController(DeliveryPartnerService deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }


    @PostMapping("/add")
    public ResponseEntity addDeliveryPartner(@RequestBody DeliveryPartnerRequest deliveryPartnerRequest){

        try {
            DeliveryPartnerResponse deliveryPartnerResponse = deliveryPartnerService.addDeliveryPartner(deliveryPartnerRequest);
            return new ResponseEntity("DeliveryBoy added successfully :" + deliveryPartnerResponse, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // give delivery partner with highest number of deliverise

    //send an email to all the parameters who have less than 10 deliveries

    // give the restaurant which have maximum number of item in their menu and which are opened also


}
