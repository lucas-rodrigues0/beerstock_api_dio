package com.digitalInnovationOneGFTStart.beerstockapi.builder;

import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.dto.WineDTO;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.enums.WineType;
import lombok.Builder;

@Builder
public class WineDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "PeTinto";

    @Builder.Default
    private String grapeVariety = "Cabernet";

    @Builder.Default
    private int max = 50;

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private WineType type = WineType.RED;

    public WineDTO toWineDTO() {
        return new WineDTO(id,
                name,
                grapeVariety,
                max,
                quantity,
                type);
    }
}
