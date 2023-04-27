package com.example.restaurantdemoapp.errors;

import com.example.restaurantdemoapp.utils.BaseError;
import com.example.restaurantdemoapp.utils.Constants;

public final class UserNotFoundException extends BaseError {

    public UserNotFoundException() {
        super(Constants.USER_NOT_FOUND);
    }
}
