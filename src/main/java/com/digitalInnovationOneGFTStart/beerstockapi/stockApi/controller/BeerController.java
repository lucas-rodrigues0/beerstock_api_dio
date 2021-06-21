package com.digitalInnovationOneGFTStart.beerstockapi.stockApi.controller;

import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.dto.BeerDTO;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.dto.QuantityDTO;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.BeerAlreadyRegisteredException;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.BeerNotFoundException;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.BeerStockExceededDecrementException;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.BeerStockExceededIncrementException;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.service.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beers")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerController {

    private final BeerService beerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO createBeer (@RequestBody @Valid BeerDTO beerDTO) throws BeerAlreadyRegisteredException {
        return beerService.createBeer(beerDTO);
    }

    @GetMapping("/{name}")
    public  BeerDTO findByName(@PathVariable String name) throws BeerNotFoundException {
        return beerService.findByName(name);
    }

    @GetMapping
    public List<BeerDTO> listBeers() {
        return  beerService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws BeerNotFoundException {
        beerService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public BeerDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws BeerNotFoundException, BeerStockExceededIncrementException {
        return beerService.increment(id, quantityDTO.getQuantity());
    }

    @PatchMapping("/{id}/decrement")
    public BeerDTO decrement(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws BeerNotFoundException, BeerStockExceededDecrementException {
        return beerService.decrement(id, quantityDTO.getQuantity());
    }
}
