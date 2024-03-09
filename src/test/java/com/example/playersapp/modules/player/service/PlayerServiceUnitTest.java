package com.example.playersapp.modules.player.service;

import com.example.playersapp.modules.player.model.query.PlayerQueryParam;
import com.example.playersapp.modules.player.repository.PlayerConditionBuilder;
import com.example.playersapp.modules.player.repository.PlayerDbRepository;
import com.example.playersapp.modules.player.model.entity.PlayerEntity;
import com.example.playersapp.modules.player.service.validations.PlayerValidations;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceUnitTest {
    @Mock
    private PlayerConditionBuilder playerConditionBuilder;

    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerDbRepository playerRepository;

    @Mock
    private PlayerValidations playerValidations;

    @Test
    public void whenGetPlayersWithPaging_thenSucceed() throws BadRequestException {
        PlayerQueryParam param = new PlayerQueryParam("Alice", "Smith", null, 0, 10);
        List<PlayerEntity> mockResults = List.of(new PlayerEntity("Alice", "Smith"));
        Optional<PageRequest> optionalPageRequest = Optional.of(Mockito.mock(PageRequest.class));
        Specification<PlayerEntity> playerEntitySpecification = Mockito.mock(Specification.class);

        when(this.playerConditionBuilder.buildSpec(any())).thenReturn(playerEntitySpecification);
        when(this.playerConditionBuilder.getPage(any(), any())).thenReturn(optionalPageRequest);

        when(playerRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(mockResults));

        List<PlayerEntity> results = playerService.getPlayers(param);

        assertEquals(1, results.size());
        verify(playerValidations).getPlayersValidation(param);
    }

    @Test
    public void whenGetPlayersWithFirstAndLastName_thenSucceed() throws BadRequestException {
        //Arrange
        PlayerQueryParam param = new PlayerQueryParam("Alice", "Smith", null, null, null);
        List<PlayerEntity> mockResults = List.of(new PlayerEntity(), new PlayerEntity());
        Optional<PageRequest> optionalPageRequest = Optional.empty();
        Specification<PlayerEntity> playerEntitySpecification = Mockito.mock(Specification.class);

        when(this.playerConditionBuilder.buildSpec(any())).thenReturn(playerEntitySpecification);
        when(this.playerConditionBuilder.getPage(any(), any())).thenReturn(optionalPageRequest);

        when(playerRepository.findAll(any(Specification.class)))
                .thenReturn(mockResults);

        //Act
        List<PlayerEntity> results = playerService.getPlayers(param);

        //Assert
        assertEquals(2, results.size());
        verify(playerValidations).getPlayersValidation(param);
    }

    @Test
    public void whenGetPlayersValidationFails_thenThrowBadRequest() throws BadRequestException {
        PlayerQueryParam param = new PlayerQueryParam(null, null, null, null, null);

        doThrow(new BadRequestException("Invalid parameters"))
                .when(playerValidations).getPlayersValidation(param);

        assertThrows(BadRequestException.class, () -> playerService.getPlayers(param));

        verify(playerValidations).getPlayersValidation(param);
        verifyNoInteractions(playerRepository);
    }

    @Test
    public void whenRepositoryThrowsException_thenShouldHandleFailure() throws BadRequestException {
        //Arrange
        PlayerQueryParam param = new PlayerQueryParam("Alice", null, null, 0, 10);

        Optional<PageRequest> optionalPageRequest = Optional.of(Mockito.mock(PageRequest.class));
        Specification<PlayerEntity> playerEntitySpecification = Mockito.mock(Specification.class);

        when(this.playerConditionBuilder.buildSpec(any())).thenReturn(playerEntitySpecification);
        when(this.playerConditionBuilder.getPage(any(), any())).thenReturn(optionalPageRequest);

        doThrow(new DataAccessException("Database access error occurred.") {})
                .when(playerRepository).findAll(any(Specification.class), any(PageRequest.class));

        //Act + Assert exception

        // Execute the service method and assert that the DataAccessException is thrown
        assertThrows(DataAccessException.class, () -> playerService.getPlayers(param));

        //Assert

        // Verify that validation was called before the exception occurred
        verify(playerValidations).getPlayersValidation(param);

        //Assert

        // Verify that the repository method was attempted to be called
        verify(playerRepository).findAll(any(Specification.class), any(PageRequest.class));
    }
}
