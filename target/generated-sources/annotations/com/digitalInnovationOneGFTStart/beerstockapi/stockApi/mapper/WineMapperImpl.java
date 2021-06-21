package com.digitalInnovationOneGFTStart.beerstockapi.stockApi.mapper;

import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.dto.WineDTO;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.entity.Wine;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-21T16:49:43-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
public class WineMapperImpl implements WineMapper {

    @Override
    public Wine toModel(WineDTO wineDTO) {
        if ( wineDTO == null ) {
            return null;
        }

        Wine wine = new Wine();

        wine.setId( wineDTO.getId() );
        wine.setName( wineDTO.getName() );
        wine.setGrapeVariety( wineDTO.getGrapeVariety() );
        wine.setMax( wineDTO.getMax() );
        wine.setQuantity( wineDTO.getQuantity() );
        wine.setType( wineDTO.getType() );

        return wine;
    }

    @Override
    public WineDTO toDTO(Wine wine) {
        if ( wine == null ) {
            return null;
        }

        WineDTO wineDTO = new WineDTO();

        wineDTO.setId( wine.getId() );
        wineDTO.setName( wine.getName() );
        wineDTO.setGrapeVariety( wine.getGrapeVariety() );
        wineDTO.setMax( wine.getMax() );
        wineDTO.setQuantity( wine.getQuantity() );
        wineDTO.setType( wine.getType() );

        return wineDTO;
    }
}
