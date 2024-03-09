package com.example.playersapp.modules.player.repository;

import com.example.playersapp.modules.player.model.entity.PlayerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.context.annotation.Import;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PlayerConditionBuilder.class)
public class PlayerRepositoryIntegrationTest {

    @Autowired
    private PlayerDbRepository playerRepository;

    @Autowired
    private PlayerConditionBuilder playerConditionBuilder;

    @Test
    public void whenSavePlayer2_thenCanBeRetrieved() {
        PlayerEntity player = new PlayerEntity();
        player.setPlayerID("aardsda01");

        playerRepository.save(player);

        List<PlayerEntity> playerEntityList = playerRepository.findAll(Example.of(player));

        assertThat(playerEntityList).isNotEmpty();
    }

    @Test
    public void whenSavePlayer_thenCanBeRetrieved() {
        PlayerEntity player = new PlayerEntity();
        player.setPlayerID("testId");
        player.setNameFirst("Elad");
        player.setNameLast("Is");

        playerRepository.save(player);

        Specification<PlayerEntity> specification = Specification.where(playerConditionBuilder.playerIdEquals("testId"));

        Optional<PlayerEntity> playerEntity = playerRepository.findOne(specification);

        assertThat(playerEntity).isPresent();
    }
}
