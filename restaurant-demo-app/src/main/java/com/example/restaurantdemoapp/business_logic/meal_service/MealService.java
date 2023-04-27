package com.example.restaurantdemoapp.business_logic.meal_service;

import com.example.restaurantdemoapp.domain.Meal;
import com.example.restaurantdemoapp.errors.GenericException;
import org.springframework.stereotype.Service;

@Service
public interface MealService {

    void createMeal(Meal meal) throws GenericException;

    Meal getMeal(String name) throws GenericException;

    void updateMeal(Meal meal) throws GenericException;

    void updateStock(String name, Integer stock) throws GenericException;

    void deleteMeal(String name) throws GenericException;
}
