package com.digitalInnovationOneGFTStart.beerstockapi.stockApi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WineType {

    RED("Red Wine"),
    WHITE("White Wine"),
    ROSE("Rose Wine"),
    SPARKLING("Sparkling Wine"),
    FORTIFIED("Fortified Wine");

    private final String description;
}
