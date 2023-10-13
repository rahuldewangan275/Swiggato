package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.requestDto.DeliveryPartnerRequest;
import com.example.Swiggato.dto.responseDto.DeliveryPartnerResponse;
import com.example.Swiggato.model.DeliveryPartner;

import java.util.ArrayList;

public class DeliveryPartnerTransformer {
    public static DeliveryPartner DeliveryPartnerRequestToDeliveryPArtner(DeliveryPartnerRequest deliveryPartnerRequest){
        return DeliveryPartner.builder()
                .name(deliveryPartnerRequest.getName())
                .mobileNo(deliveryPartnerRequest.getMobile())
                .gender(deliveryPartnerRequest.getGender())
                .orders(new ArrayList<>())
                .build();
    }

    public static DeliveryPartnerResponse DeliveryPartnerToDeliveryPartnerResponse(DeliveryPartner deliveryPartner){
        return DeliveryPartnerResponse.builder()
                .name(deliveryPartner.getName())
                .mobile(deliveryPartner.getMobileNo())
                .build();
    }
}
