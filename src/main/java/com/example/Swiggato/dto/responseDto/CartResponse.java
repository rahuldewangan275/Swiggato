package com.example.Swiggato.dto.responseDto;

import com.example.Swiggato.model.FoodItem;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    double cartTotal;
    List<FoodResponse> foodList;
}
