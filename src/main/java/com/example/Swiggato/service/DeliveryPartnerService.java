package com.example.Swiggato.service;

import com.example.Swiggato.dto.requestDto.DeliveryPartnerRequest;
import com.example.Swiggato.dto.responseDto.DeliveryPartnerResponse;
import com.example.Swiggato.exception.DeliveryPartnerAlreadyExistException;
import com.example.Swiggato.model.DeliveryPartner;
import com.example.Swiggato.repository.DeliveryPartnerRepository;
import com.example.Swiggato.transformer.DeliveryPartnerTransformer;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerService {
final DeliveryPartnerRepository deliveryPartnerRepository;
    public DeliveryPartnerService(DeliveryPartnerRepository deliveryPartnerRepository) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }

    public DeliveryPartnerResponse addDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {

        //check delivery partner already exist. if exist then throw exception
        String mobile = deliveryPartnerRequest.getMobile();
        DeliveryPartner chekDeliveryPartner = deliveryPartnerRepository.findByMobileNo(mobile);

        if(chekDeliveryPartner != null){
            throw new DeliveryPartnerAlreadyExistException("Delivery Partner already Registered");
        }
   // dto to Model
        DeliveryPartner deliveryPartner = DeliveryPartnerTransformer.DeliveryPartnerRequestToDeliveryPArtner(deliveryPartnerRequest);

        //save delivery Partner
        DeliveryPartner saved = deliveryPartnerRepository.save(deliveryPartner);

        DeliveryPartnerResponse deliveryPartnerResponse = DeliveryPartnerTransformer.DeliveryPartnerToDeliveryPartnerResponse(saved);

        return deliveryPartnerResponse;

    }
}
