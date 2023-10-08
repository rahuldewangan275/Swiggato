package com.example.Swiggato.dto.requestDto;

import com.example.Swiggato.Enum.FoodCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequest {
    String dishName;
    double price;
    FoodCategory foodCategory;
    boolean veg;
    boolean available;

}
