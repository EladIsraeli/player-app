package com.example.playersapp.modules.player;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class E2EPlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetRequestToPlayersWithQueryParams_thenReturnsFilteredPlayers() throws Exception {
        mockMvc.perform(get("/players?page=0&size=100&firstName=Cody"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].playerID").value("allenco01"));
    }

    @Test
    public void whenGetRequestToPlayerById_thenReturnsPlayerDetails() throws Exception {
        mockMvc.perform(get("/players/allenco01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].playerID").value("allenco01"));
    }
}
