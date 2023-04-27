package com.example.restaurantdemoapp.errors;

import com.example.restaurantdemoapp.utils.BaseError;
import com.example.restaurantdemoapp.utils.Constants;

public class OutOfStockException extends BaseError {

    public OutOfStockException () {
        super (Constants.OUT_OF_STOCK);
    }
}
