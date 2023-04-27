package com.example.restaurantdemoapp.business_logic.user_service;

import com.example.restaurantdemoapp.domain.User;
import com.example.restaurantdemoapp.errors.CreateAccountException;
import com.example.restaurantdemoapp.errors.LoginException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User login(String username, String password) throws LoginException;

    void createAccount(User user) throws CreateAccountException;
}
