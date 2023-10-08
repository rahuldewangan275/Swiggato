package com.example.Swiggato.dto.requestDto;

import com.example.Swiggato.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    String name ;
    Gender gender;
    String email;
    String phoneNo;
    String address;

}
