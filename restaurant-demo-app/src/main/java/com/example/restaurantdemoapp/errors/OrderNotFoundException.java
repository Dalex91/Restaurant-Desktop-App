package com.example.restaurantdemoapp.errors;

import com.example.restaurantdemoapp.utils.BaseError;
import com.example.restaurantdemoapp.utils.Constants;

public class OrderNotFoundException extends BaseError {
    public OrderNotFoundException () {
        super (Constants.ORDER_NOT__FOUND);
    }
}
