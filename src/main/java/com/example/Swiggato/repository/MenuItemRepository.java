package com.example.Swiggato.repository;

import com.example.Swiggato.Enum.FoodCategory;
import com.example.Swiggato.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Integer> {
    // passed food category bcoz this is HQL
    @Query(value = "select fi from MenuItem fi where fi.foodCategory = :foodCategory  and  fi.price >= :priceAbove")
    List<MenuItem> getAllFoodsOfParticularCategoryAndPriceAbove(FoodCategory foodCategory, double priceAbove);

    //getting all nonveg foods
    @Query(value = "select fi from MenuItem fi where fi.veg=true")
    List<MenuItem> getAllNonVegFoods();

    //getting all veg foods
    @Query(value = "select fi from MenuItem fi where fi.veg=false")
    List<MenuItem> getAllVegFoods();



    //sql querry
    @Query(value = "select * from menu_item ORDER BY price ASC",nativeQuery = true)
    List<MenuItem> priceLowToHigh();

    @Query(value = "SELECT * FROM menu_item WHERE restaurant_id = :id  ORDER BY price ",nativeQuery = true)
    List<MenuItem> sortRestaurantFoodByPrice(int id);
}
