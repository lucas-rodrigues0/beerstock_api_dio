package com.digitalInnovationOneGFTStart.beerstockapi.mapper;

import com.digitalInnovationOneGFTStart.beerstockapi.dto.BeerDTO;
import com.digitalInnovationOneGFTStart.beerstockapi.entity.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeerMapper {

    BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);

    Beer toModel(BeerDTO beerDTO);

    BeerDTO toDTO(Beer beer);
}
