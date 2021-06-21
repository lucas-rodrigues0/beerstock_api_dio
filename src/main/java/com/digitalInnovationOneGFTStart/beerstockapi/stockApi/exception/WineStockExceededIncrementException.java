package com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WineStockExceededIncrementException extends Exception {

    public WineStockExceededIncrementException(Long id, int quantityToDecrement) {
        super(String.format("Wine with %s ID to increment informed exceeds the max stock capacity: %s", id, quantityToDecrement));
    }
}
