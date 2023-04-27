package com.example.restaurantdemoapp.repository;

import com.example.restaurantdemoapp.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, String> {

    @Modifying
    @Query("UPDATE Meal SET stock = :stock, price = :price WHERE name = :name")
    int updateMeal(@Param("name") String name, @Param("stock") Integer stock, @Param("price") Double price);

    @Modifying
    @Query("UPDATE Meal SET stock = :stock WHERE name = :name")
    int updateStock(@Param ("name") String name, @Param("stock") Integer stock);
}
