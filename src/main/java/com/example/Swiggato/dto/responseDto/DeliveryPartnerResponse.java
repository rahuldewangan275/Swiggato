package com.example.Swiggato.dto.responseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerResponse {

    String name;
    String mobile;
}
