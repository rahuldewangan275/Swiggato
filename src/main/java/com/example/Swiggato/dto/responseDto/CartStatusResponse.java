package com.example.Swiggato.dto.responseDto;

import java.util.List;

public class CartStatusResponse {
     String customerName;
     String customerAddress;
     String customerMobile;
     int cartTotal;
     List<FoodResponse>foodResponseList;
     String restaurantName;
}
