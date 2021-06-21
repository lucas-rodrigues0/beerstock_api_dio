package com.digitalInnovationOneGFTStart.beerstockapi.stockApi.controller;

import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.dto.QuantityDTO;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.dto.WineDTO;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.service.WineService;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.WineAlreadyRegisteredException;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.WineNotFoundException;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.WineStockExceededDecrementException;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.WineStockExceededIncrementException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wines")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WineController {

    private final WineService wineService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WineDTO createWine (@RequestBody @Valid WineDTO wineDTO) throws WineAlreadyRegisteredException {
        return wineService.createWine(wineDTO);
    }

    @GetMapping("/{name}")
    public  WineDTO findByName(@PathVariable String name) throws WineNotFoundException {
        return wineService.findByName(name);
    }

    @GetMapping
    public List<WineDTO> listWines() {
        return  wineService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws WineNotFoundException {
        wineService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public WineDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws WineNotFoundException, WineStockExceededIncrementException {
        return wineService.incrementWine(id, quantityDTO.getQuantity());
    }

    @PatchMapping("/{id}/decrement")
    public WineDTO decrement(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws WineNotFoundException, WineStockExceededDecrementException {
        return wineService.decrementWine(id, quantityDTO.getQuantity());
    }

}
