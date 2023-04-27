package com.example.restaurantdemoapp.errors;

import com.example.restaurantdemoapp.utils.BaseError;
import com.example.restaurantdemoapp.utils.Constants;


public final class CreateAccountException extends BaseError {

    public CreateAccountException() {
        super(Constants.CREATE_ACCOUNT_FAILED);
    }
}
