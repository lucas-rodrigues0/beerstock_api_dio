package com.digitalInnovationOneGFTStart.beerstockapi.stockApi.dto;

import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.enums.WineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WineDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    private String name;

    @NotNull
    @Size(min = 1, max = 200)
    private String grapeVariety;

    @NotNull
    @Max(500)
    private int max;

    @NotNull
    @Max(100)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @NotNull
    private WineType type;
}
