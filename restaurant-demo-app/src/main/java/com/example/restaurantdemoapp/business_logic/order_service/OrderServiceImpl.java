package com.example.restaurantdemoapp.business_logic.order_service;

import com.example.restaurantdemoapp.domain.Meal;
import com.example.restaurantdemoapp.domain.Order;
import com.example.restaurantdemoapp.domain.OrderStatus;
import com.example.restaurantdemoapp.errors.GenericException;
import com.example.restaurantdemoapp.errors.OrderNotFoundException;
import com.example.restaurantdemoapp.errors.OutOfStockException;
import com.example.restaurantdemoapp.repository.MealRepository;
import com.example.restaurantdemoapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("orderService")
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MealRepository mealRepository;

    @Override
    public void createOrder (Integer id, OrderStatus status, List<Meal> mealList) throws GenericException, OutOfStockException {
        List<Meal> uniqueMeals = mealList.stream()
                .collect(Collectors.groupingBy(
                        Meal::getName,
                        Collectors.collectingAndThen(
                                Collectors.reducing((m1, m2) -> new Meal(
                                        m1.getName(),
                                        m1.getPrice(),
                                        m1.getStock() + m2.getStock()
                                )),
                                Optional::get
                        )
                ))
                .values().stream()
                .toList();

        Double total = uniqueMeals.stream()
                .mapToDouble(meal -> meal.getPrice() * meal.getStock())
                .sum();

        for(Meal meal: uniqueMeals) {
            Optional<Meal> response = mealRepository.findById (meal.getName ());
            if (response.isPresent ()) {
                Meal dbMeal = response.get ();
                if(dbMeal.getStock () < meal.getStock ())
                    throw new OutOfStockException ();
            }
            else
                throw new GenericException ();
        }

        orderRepository.save (new Order (
                id,
                total,
                Timestamp.from (Instant.now ()),
                status,
                uniqueMeals
        ));
    }

    @Override
    public void updateStatus (Integer id, OrderStatus order) {
        orderRepository.updateStock (id, order);
    }

    @Override
    public Order getOrder (Integer id) throws OrderNotFoundException {
        Optional<Order> response = orderRepository.findById (id);
        if (response.isPresent ())
            return response.get ();
        else
            throw new OrderNotFoundException ();
    }

    @Override
    public Integer getLastIdOrZero () {
        return Math.toIntExact (orderRepository.count ());
    }

    @Override
    public List<Order> getOrdersBetweenTwoDates (Timestamp startDate, Timestamp endDate) {
        return orderRepository.findOrdersBetweenTimestamps (startDate, endDate);
    }
}
