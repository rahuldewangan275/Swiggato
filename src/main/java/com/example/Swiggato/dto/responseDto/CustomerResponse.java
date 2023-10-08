package com.example.Swiggato.dto.responseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    String name ;
    String phoneNo;
    String address;
    CartResponse cartResponse;
}
