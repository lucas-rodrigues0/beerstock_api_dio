package com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WineStockExceededDecrementException extends Exception {

    public WineStockExceededDecrementException(Long id, int quantityToDecrement) {
        super(String.format("Wine with %s ID to decrement informed exceeds the min stock capacity: %s", id, quantityToDecrement));
    }
}
