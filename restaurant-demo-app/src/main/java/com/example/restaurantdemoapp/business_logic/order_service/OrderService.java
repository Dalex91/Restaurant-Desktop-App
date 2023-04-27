package com.example.restaurantdemoapp.business_logic.order_service;

import com.example.restaurantdemoapp.domain.Meal;
import com.example.restaurantdemoapp.domain.Order;
import com.example.restaurantdemoapp.domain.OrderStatus;
import com.example.restaurantdemoapp.errors.GenericException;
import com.example.restaurantdemoapp.errors.OrderNotFoundException;
import com.example.restaurantdemoapp.errors.OutOfStockException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface OrderService {

    void createOrder(Integer id, OrderStatus status, List<Meal> mealList) throws GenericException, OutOfStockException;

    void updateStatus(Integer id, OrderStatus order) throws GenericException;

    Order getOrder(Integer id) throws GenericException, OrderNotFoundException;

    Integer getLastIdOrZero();

    List<Order> getOrdersBetweenTwoDates(Timestamp startDate, Timestamp endDate);
}
