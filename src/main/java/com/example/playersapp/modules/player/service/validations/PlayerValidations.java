package com.example.playersapp.modules.player.service.validations;

import com.example.playersapp.common.error.ExceptionNotFound;
import com.example.playersapp.modules.player.model.query.PlayerQueryParam;
import com.example.playersapp.modules.player.repository.PlayerConditionBuilder;
import com.example.playersapp.modules.player.repository.PlayerDbRepository;
import com.example.playersapp.modules.player.model.entity.PlayerEntity;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlayerValidations {

    @Autowired
    private PlayerDbRepository playerRepository;

    @Autowired
    private PlayerConditionBuilder playerConditionBuilder;

    public void getPlayersValidation(PlayerQueryParam playerQueryParam) throws BadRequestException {
        if(playerQueryParam.getPlayerID() != null) {
            Specification<PlayerEntity> spec = Specification.where(this.playerConditionBuilder.playerIdEquals(playerQueryParam.getPlayerID()));

            Optional<PlayerEntity> playerEntity = this.playerRepository.findOne(spec);
            if(playerEntity.isEmpty()) {
                throw new ExceptionNotFound("Player Id Is Not Exists");
            }
        }

        if(playerQueryParam.getFirstName() != null && (playerQueryParam.getFirstName().isEmpty() || playerQueryParam.getFirstName().isBlank())) {
            throw new BadRequestException("Player first name is Blank or Empty, please change the given query param");
        }

        if(playerQueryParam.getLastName() != null && (playerQueryParam.getLastName().isEmpty() || playerQueryParam.getLastName().isBlank())) {
            throw new BadRequestException("Player last name is Blank or Empty, please change the given query param");
        }
    }
}
