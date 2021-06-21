package com.digitalInnovationOneGFTStart.beerstockapi.service;

import com.digitalInnovationOneGFTStart.beerstockapi.builder.WineDTOBuilder;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.dto.WineDTO;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.entity.Wine;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.*;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.exception.WineAlreadyRegisteredException;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.mapper.WineMapper;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.repository.WineRepository;
import com.digitalInnovationOneGFTStart.beerstockapi.stockApi.service.WineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WineServiceTest {
    private static final long INVALID_WINE_ID = 1L;

    @Mock
    private WineRepository wineRepository;

    private WineMapper wineMapper = WineMapper.INSTANCE;

    @InjectMocks
    private WineService wineService;

    @Test
    void whenWineInformedThenItShouldBeCreated() throws WineAlreadyRegisteredException {
        // given
        WineDTO expectedWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedSavedWine = wineMapper.toModel(expectedWineDTO);

        // when
        when(wineRepository.findByName(expectedWineDTO.getName())).thenReturn(Optional.empty());
        when(wineRepository.save(expectedSavedWine)).thenReturn(expectedSavedWine);

        //then
        WineDTO createdWineDTO = wineService.createWine(expectedWineDTO);

        assertThat(createdWineDTO.getId(), is(equalTo(expectedWineDTO.getId())));
        assertThat(createdWineDTO.getName(), is(equalTo(expectedWineDTO.getName())));
        assertThat(createdWineDTO.getQuantity(), is(equalTo(expectedWineDTO.getQuantity())));
    }

    @Test
    void whenAlreadyRegisteredWineInformedThenAnExceptionShouldBeThrown() {
        // given
        WineDTO expectedWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine duplicatedWine = wineMapper.toModel(expectedWineDTO);

        // when
        when(wineRepository.findByName(expectedWineDTO.getName())).thenReturn(Optional.of(duplicatedWine));

        // then
        assertThrows(WineAlreadyRegisteredException.class, () -> wineService.createWine(expectedWineDTO));
    }

    @Test
    void whenValidWineNameIsGivenThenReturnABeer() throws WineNotFoundException {
        // given
        WineDTO expectedFoundWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedFoundWine = wineMapper.toModel(expectedFoundWineDTO);

        // when
        when(wineRepository.findByName(expectedFoundWine.getName())).thenReturn(Optional.of(expectedFoundWine));

        // then
        WineDTO foundWineDTO = wineService.findByName(expectedFoundWineDTO.getName());

        assertThat(foundWineDTO, is(equalTo(expectedFoundWineDTO)));
    }

    @Test
    void whenNotRegisteredWineNameIsGivenThenThrowAnException() {
        // given
        WineDTO expectedFoundWineDTO = WineDTOBuilder.builder().build().toWineDTO();

        // when
        when(wineRepository.findByName(expectedFoundWineDTO.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(WineNotFoundException.class, () -> wineService.findByName(expectedFoundWineDTO.getName()));
    }

    @Test
    void whenListWineIsCalledThenReturnAListOfWines() {
        // given
        WineDTO expectedFoundWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedFoundWine = wineMapper.toModel(expectedFoundWineDTO);

        //when
        when(wineRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundWine));

        //then
        List<WineDTO> foundListBeersDTO = wineService.listAll();

        assertThat(foundListBeersDTO, is(not(empty())));
        assertThat(foundListBeersDTO.get(0), is(equalTo(expectedFoundWineDTO)));
    }

    @Test
    void whenListWineIsCalledThenReturnAnEmptyListOfWines() {
        //when
        when(wineRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<WineDTO> foundListWinesDTO = wineService.listAll();

        assertThat(foundListWinesDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAWineShouldBeDeleted() throws WineNotFoundException{
        // given
        WineDTO expectedDeletedWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedDeletedWine = wineMapper.toModel(expectedDeletedWineDTO);

        // when
        when(wineRepository.findById(expectedDeletedWineDTO.getId())).thenReturn(Optional.of(expectedDeletedWine));
        doNothing().when(wineRepository).deleteById(expectedDeletedWineDTO.getId());

        // then
        wineService.deleteById(expectedDeletedWineDTO.getId());

        verify(wineRepository, times(1)).findById(expectedDeletedWineDTO.getId());
        verify(wineRepository, times(1)).deleteById(expectedDeletedWineDTO.getId());
    }

    @Test
    void whenIncrementIsCalledThenIncrementWineStock() throws WineNotFoundException, WineStockExceededIncrementException {
        //given
        WineDTO expectedWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedWine = wineMapper.toModel(expectedWineDTO);

        //when
        when(wineRepository.findById(expectedWineDTO.getId())).thenReturn(Optional.of(expectedWine));
        when(wineRepository.save(expectedWine)).thenReturn(expectedWine);

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = expectedWineDTO.getQuantity() + quantityToIncrement;

        // then
        WineDTO incrementedWineDTO = wineService.incrementWine(expectedWineDTO.getId(), quantityToIncrement);

        assertThat(expectedQuantityAfterIncrement, equalTo(incrementedWineDTO.getQuantity()));
        assertThat(expectedQuantityAfterIncrement, lessThan(expectedWineDTO.getMax()));
    }

    @Test
    void whenIncrementIsGreatherThanMaxThenThrowException() {
        // given
        WineDTO expectedWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedWine = wineMapper.toModel(expectedWineDTO);

        //when
        when(wineRepository.findById(expectedWineDTO.getId())).thenReturn(Optional.of(expectedWine));

        int quantityToIncrement = 80;

        // then
        assertThrows(WineStockExceededIncrementException.class, () -> wineService.incrementWine(expectedWineDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementAfterSumIsGreatherThanMaxThenThrowException() {
        // given
        WineDTO expectedWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedWine = wineMapper.toModel(expectedWineDTO);

        // when
        when(wineRepository.findById(expectedWineDTO.getId())).thenReturn(Optional.of(expectedWine));

        int quantityToIncrement = 45;

        // then
        assertThrows(WineStockExceededIncrementException.class, () -> wineService.incrementWine(expectedWineDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementIsCalledWithInvalidIdThenThrowException() {
        int quantityToIncrement = 10;

        // when
        when(wineRepository.findById(INVALID_WINE_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(WineNotFoundException.class, () -> wineService.incrementWine(INVALID_WINE_ID, quantityToIncrement));
    }

    @Test
    void whenDecrementIsCalledThenDecrementWineStock() throws WineNotFoundException, WineStockExceededDecrementException {
        // given
        WineDTO expectedWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedWine = wineMapper.toModel(expectedWineDTO);

        // when
        when(wineRepository.findById(expectedWineDTO.getId())).thenReturn(Optional.of(expectedWine));
        when(wineRepository.save(expectedWine)).thenReturn(expectedWine);

        int quantityToDecrement = 5;
        int expectedQuantityAfterDecrement = expectedWineDTO.getQuantity() - quantityToDecrement;

        // then
        WineDTO incrementedWineDTO = wineService.decrementWine(expectedWineDTO.getId(), quantityToDecrement);

        assertThat(expectedQuantityAfterDecrement, equalTo(incrementedWineDTO.getQuantity()));
        assertThat(expectedQuantityAfterDecrement, greaterThan(0));
    }

    @Test
    void whenDecrementIsCalledToEmptyStockThenEmptyWineStock() throws WineNotFoundException, WineStockExceededDecrementException {
        // given
        WineDTO expectedWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedWine = wineMapper.toModel(expectedWineDTO);

        // when
        when(wineRepository.findById(expectedWineDTO.getId())).thenReturn(Optional.of(expectedWine));
        when(wineRepository.save(expectedWine)).thenReturn(expectedWine);

        int quantityToDecrement = 10;
        int expectedQuantityAfterDecrement = expectedWineDTO.getQuantity() - quantityToDecrement;

        // then
        WineDTO incrementedWineDTO = wineService.decrementWine(expectedWineDTO.getId(), quantityToDecrement);

        assertThat(expectedQuantityAfterDecrement, equalTo(0));
        assertThat(expectedQuantityAfterDecrement, equalTo(incrementedWineDTO.getQuantity()));
    }

    @Test
    void whenDecrementIsLowerThanZeroThenThrowException() {
        // given
        WineDTO expectedWineDTO = WineDTOBuilder.builder().build().toWineDTO();
        Wine expectedWine = wineMapper.toModel(expectedWineDTO);

        // when
        when(wineRepository.findById(expectedWineDTO.getId())).thenReturn(Optional.of(expectedWine));

        int quantityToDecrement = 80;

        // then
        assertThrows(WineStockExceededDecrementException.class, () -> wineService.decrementWine(expectedWineDTO.getId(), quantityToDecrement));
    }

    @Test
    void whenDecrementIsCalledWithInvalidIdThenThrowException() {
        int quantityToDecrement = 10;

        // when
        when(wineRepository.findById(INVALID_WINE_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(WineNotFoundException.class, () -> wineService.decrementWine(INVALID_WINE_ID, quantityToDecrement));
    }

}
