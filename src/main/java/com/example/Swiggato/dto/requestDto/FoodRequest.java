package com.example.Swiggato.dto.requestDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequest {
    int requiredQuantity;

    String customerMobile; // to get customer details

    int menuItemId;

}
