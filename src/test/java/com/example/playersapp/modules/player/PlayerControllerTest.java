package com.example.playersapp.modules.player;

import com.example.playersapp.common.error.ExceptionNotFound;
import com.example.playersapp.modules.player.controller.PlayersController;
import com.example.playersapp.modules.player.model.entity.PlayerEntity;
import com.example.playersapp.modules.player.service.PlayerService;
import com.example.playersapp.utils.csv.CsvImporter;
import com.example.playersapp.utils.health.ApplicationReadinessService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PlayersController.class)
class PlayerControllerTest {

    @MockBean
    @Qualifier("ThreadedChunksCsvImporter")
    private CsvImporter csvImporter;

    @MockBean
    private ApplicationReadinessService readinessService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Test
    void testGetAllPlayers() throws Exception {
        when(readinessService.isReady()).thenReturn(true);
        when(playerService.getPlayers(any())).thenReturn(List.of(new PlayerEntity("e1"), new PlayerEntity("e2")));

        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].playerID", is("e1")))
                .andExpect(jsonPath("$.data[1].playerID", is("e2")));

    }

    @Test
    void testGetAllPlayers_failureBadRequest() throws Exception {
        when(readinessService.isReady()).thenReturn(true);
        when(playerService.getPlayers(any())).thenThrow(new BadRequestException("error"));

        mockMvc.perform(get("/players"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testGetAllPlayers_failureInternalServerError() throws Exception {
        when(readinessService.isReady()).thenReturn(true);
        when(playerService.getPlayers(any())).thenThrow(new RuntimeException("internal error!"));

        mockMvc.perform(get("/players"))
                .andExpect(status().isInternalServerError());

    }
    @Test
    public void testGetPlayerId() throws Exception {
        when(readinessService.isReady()).thenReturn(true);
        when(playerService.getPlayers(any())).thenReturn(List.of(new PlayerEntity("allenco01"), new PlayerEntity("e2")));


        mockMvc.perform(get("/players/allenco01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].playerID").value("allenco01"));
    }

    @Test
    public void testGetPlayerId_NotFoundError() throws Exception {
        when(readinessService.isReady()).thenReturn(true);
        when(playerService.getPlayers(any())).thenThrow(new ExceptionNotFound("the player id is not exist"));

        mockMvc.perform(get("/players/gibrish"))
                .andExpect(status().isNotFound());
    }
}
