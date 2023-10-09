package com.example.Swiggato.repository;

import com.example.Swiggato.Enum.FoodCategory;
import com.example.Swiggato.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem,Integer> {
    // passed food category bcoz this is HQL
    @Query(value = "select fi from FoodItem fi where fi.foodCategory = :foodCategory  and  fi.price >= :priceAbove")
    List<FoodItem> getAllFoodsOfParticularCategoryAndPriceAbove(FoodCategory foodCategory, double priceAbove);

    //getting all nonveg foods
    @Query(value = "select fi from FoodItem fi where fi.veg=true")
    List<FoodItem> getAllNonVegFoods();

    //getting all veg foods
    @Query(value = "select fi from FoodItem fi where fi.veg=false")
    List<FoodItem> getAllVegFoods();



    //sql querry
    @Query(value = "select * from food_item ORDER BY price ASC",nativeQuery = true)
    List<FoodItem> priceLowToHigh();

    @Query(value = "SELECT * FROM food_item WHERE restaurant_id = :id  ORDER BY price ",nativeQuery = true)
    List<FoodItem> sortRestaurantFoodByPrice(int id);
}
