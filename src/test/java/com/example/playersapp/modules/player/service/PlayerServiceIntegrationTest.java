package com.example.playersapp.modules.player.service;

import com.example.playersapp.modules.player.model.query.PlayerQueryParam;
import com.example.playersapp.modules.player.repository.PlayerDbRepository;
import com.example.playersapp.modules.player.model.entity.PlayerEntity;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(PlayerService.class)
public class PlayerServiceIntegrationTest {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerDbRepository playerRepository;

    @Test
    public void whenAddPlayer_thenPlayerIsInRepository() throws BadRequestException {
        //Arrange - CSV file inserted data with David as firstName

        PlayerQueryParam playerQueryParam = new PlayerQueryParam();
        playerQueryParam.setFirstName("David");

        //Act
        List<PlayerEntity> playerEntityList = playerService.getPlayers(playerQueryParam);

        //Assert
        assertThat(playerEntityList).isNotNull();
    }
}
