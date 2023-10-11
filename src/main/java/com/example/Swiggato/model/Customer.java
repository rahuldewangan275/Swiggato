package com.example.Swiggato.model;

import com.example.Swiggato.Enum.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Size(min=2,message = "{validation.name.size_too_short}")
    @Size(max=40,message = "{validation.name.size_too_long}")

    String name ;

    @Enumerated(EnumType.STRING)
    Gender gender;
    @Email //jakarta validation constraint
    @Column(unique = true)
    String email;

    @Column(unique = true,nullable = false)
    @Size(min = 10,max=10)
    String phoneNo;

    String address;

    // entity relationship
    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    Cart cart;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<OrderEntity> orders = new ArrayList<>();

}
