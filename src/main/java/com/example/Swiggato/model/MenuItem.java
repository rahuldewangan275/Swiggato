package com.example.Swiggato.model;

import com.example.Swiggato.Enum.FoodCategory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name = "menu_item")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String dishName;

    double price;

    @Enumerated(EnumType.STRING)
    FoodCategory foodCategory;

    boolean veg;

    boolean available;

    // cardinality relation
//    @ManyToOne
//    @JoinColumn
//    Cart cart;
//
//    @ManyToOne
//     @JoinColumn
//    OrderEntity order;

    @ManyToOne
    @JoinColumn
    Restaurant restaurant;

    @OneToMany(mappedBy = "menuItem",cascade = CascadeType.ALL)
    List<FoodItem> foodItemList = new ArrayList<>();

}
