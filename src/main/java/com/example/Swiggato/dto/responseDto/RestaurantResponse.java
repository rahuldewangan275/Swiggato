package com.example.Swiggato.dto.responseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantResponse {
    String name;
    String location;
    String contactNumber;
    boolean opened;
    List<MenuResponse> menu ;

}
