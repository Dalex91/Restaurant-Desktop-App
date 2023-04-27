package com.example.restaurantdemoapp.errors;

import com.example.restaurantdemoapp.utils.BaseError;
import com.example.restaurantdemoapp.utils.Constants;

public class GenericException extends BaseError {

    public GenericException() {
        super(Constants.GENERIC_ERROR);
    }
}
