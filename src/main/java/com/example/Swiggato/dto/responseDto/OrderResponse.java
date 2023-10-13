package com.example.Swiggato.dto.responseDto;

import com.example.Swiggato.model.FoodItem;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    String orderId;
    double orderTotal;
    Date orderTime;

    String customerName;
    String customerMobile;

    String deliveryPartnerName;
    String deliveryPartnerMobile;
    String restaurantName;

    List<FoodResponse> foodResponseList;
}
