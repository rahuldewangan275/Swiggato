package com.example.Swiggato.dto.requestDto;

import com.example.Swiggato.Enum.RestaurantCategory;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantRequest {
    String name;
    String location;
    RestaurantCategory restaurantCategory;
    String contactNumber;
}
