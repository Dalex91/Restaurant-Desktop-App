package com.example.restaurantdemoapp.business_logic.meal_service;

import com.example.restaurantdemoapp.domain.Meal;
import com.example.restaurantdemoapp.errors.GenericException;
import com.example.restaurantdemoapp.repository.MealRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service("mealService")
public class MealServiceImpl implements MealService{

    @Autowired
    private MealRepository mealRepository;

    @Override
    public void createMeal (Meal meal) throws GenericException {
        Meal addedMeal = mealRepository.save (meal);
        if (addedMeal.getName () == null)
            throw new GenericException ();
    }

    @Override
    public Meal getMeal (String name) throws GenericException {
        Optional<Meal> response = mealRepository.findById (name);
        if (response.isPresent ())
            return response.get ();
        else
            throw new GenericException ();
    }

    @Transactional
    @Override
    public void updateMeal (Meal meal) throws GenericException {
        if(mealRepository.updateMeal (meal.getName (), meal.getStock (), meal.getPrice ()) == 0)
            throw new GenericException ();
    }

    @Transactional
    @Override
    public void updateStock (String name, Integer stock) throws GenericException {
        if(mealRepository.updateStock (name, stock) == 0)
            throw new GenericException ();
    }

    @Transactional
    @Override
    public void deleteMeal (String name) throws GenericException {
        mealRepository.deleteById (name);
        if (mealRepository.existsById (name))
            throw new GenericException ();
    }
}
