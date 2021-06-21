package com.digitalInnovationOneGFTStart.beerstockapi.stockApi.service;

import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.dto.WineDTO;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.entity.Wine;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.mapper.WineMapper;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.repository.WineRepository;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.WineAlreadyRegisteredException;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.WineNotFoundException;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.WineStockExceededDecrementException;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.WineStockExceededIncrementException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WineService {

    private final WineRepository wineRepository;
    private final WineMapper wineMapper = WineMapper.INSTANCE;

    public WineDTO createWine(WineDTO wineDTO) throws WineAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(wineDTO.getName());
        Wine wine = wineMapper.toModel(wineDTO);
        Wine savedWine = wineRepository.save(wine);
        return wineMapper.toDTO(savedWine);
    }

    public WineDTO findByName(String name) throws WineNotFoundException {
        Wine foundWine = wineRepository.findByName(name)
                .orElseThrow(() -> new WineNotFoundException(name));
        return wineMapper.toDTO(foundWine);
    }

    public List<WineDTO> listAll() {
        return wineRepository.findAll()
                .stream()
                .map(wineMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws WineNotFoundException {
        verifyIfExists(id);
        wineRepository.deleteById(id);
    }

    public WineDTO incrementWine(Long id, int quantityToIncrement) throws WineNotFoundException, WineStockExceededIncrementException {
        Wine wineToIncrementStock = verifyIfExists(id);
        int quantityAfterIncrement = quantityToIncrement + wineToIncrementStock.getQuantity();
        if (quantityAfterIncrement <= wineToIncrementStock.getMax()) {
            wineToIncrementStock.setQuantity(quantityAfterIncrement);
            Wine incrementedWineStock = wineRepository.save(wineToIncrementStock);
            return wineMapper.toDTO(incrementedWineStock);
        }
        throw  new WineStockExceededIncrementException(id, quantityToIncrement);
    }

    public WineDTO decrementWine(Long id, int quantityToDecrement) throws WineNotFoundException, WineStockExceededDecrementException {
        Wine wineToDecrementStock = verifyIfExists(id);
        int quantityAfterDecrement = wineToDecrementStock.getQuantity() - quantityToDecrement;
        if (quantityAfterDecrement >= 0) {
            wineToDecrementStock.setQuantity(quantityAfterDecrement);
            Wine decrementedWineStock = wineRepository.save(wineToDecrementStock);
            return wineMapper.toDTO(decrementedWineStock);
        }
        throw new WineStockExceededDecrementException(id, quantityToDecrement);
    }

    private void verifyIfIsAlreadyRegistered(String name) throws WineAlreadyRegisteredException {
        Optional<Wine> optSavedWine = wineRepository.findByName(name);
        if(optSavedWine.isPresent()) {
            throw new WineAlreadyRegisteredException(name);
        }
    }

    private Wine verifyIfExists(Long id) throws WineNotFoundException {
        return wineRepository.findById(id)
                .orElseThrow(() -> new WineNotFoundException(id));
    }


}
