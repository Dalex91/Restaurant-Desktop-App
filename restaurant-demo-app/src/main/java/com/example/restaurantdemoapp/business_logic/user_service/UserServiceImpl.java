package com.example.restaurantdemoapp.business_logic.user_service;

import com.example.restaurantdemoapp.domain.User;
import com.example.restaurantdemoapp.errors.CreateAccountException;
import com.example.restaurantdemoapp.errors.LoginException;
import com.example.restaurantdemoapp.repository.UserRepository;
import com.example.restaurantdemoapp.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login (String name, String password) throws LoginException {
        Optional<User> result = userRepository.findById (name);
        if (result.isPresent ()) {
            User currentUser = result.get ();
            if (! currentUser.getPassword ().equals (Utils.encrypt (password)))
                throw new LoginException ();
            return currentUser;
        }
        else
            throw new LoginException ();
    }

    @Override
    public void createAccount (User user) throws CreateAccountException {
        User savedUser = userRepository.save (user);
        if(savedUser.getUsername () == null)
            throw new CreateAccountException ();
    }
}
