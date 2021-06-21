package com.digitalInnovationOneGFTStart.beerstockapi.stockApi.mapper;

import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.dto.WineDTO;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.entity.Wine;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WineMapper {

    WineMapper INSTANCE = Mappers.getMapper(WineMapper.class);

    Wine toModel(WineDTO wineDTO);

    WineDTO toDTO(Wine wine);
}
