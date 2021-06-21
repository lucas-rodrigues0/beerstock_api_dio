package com.digitalInnovationOneGFTStart.beerstockapi.stockApi.mapper;

import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.dto.BeerDTO;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.entity.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeerMapper {

    BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);

    Beer toModel(BeerDTO beerDTO);

    BeerDTO toDTO(Beer beer);
}
