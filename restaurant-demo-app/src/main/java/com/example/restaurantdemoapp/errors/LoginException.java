package com.example.restaurantdemoapp.errors;

import com.example.restaurantdemoapp.utils.BaseError;
import com.example.restaurantdemoapp.utils.Constants;

public final class LoginException extends BaseError {

    public LoginException() {
        super(Constants.LOGIN_FAILED);
    }
}
