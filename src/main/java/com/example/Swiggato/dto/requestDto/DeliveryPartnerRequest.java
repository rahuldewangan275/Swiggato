package com.example.Swiggato.dto.requestDto;

import com.example.Swiggato.Enum.Gender;
import com.example.Swiggato.model.DeliveryPartner;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerRequest {

    String name;
    String mobile;
    Gender gender;


}
