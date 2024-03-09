package com.example.playersapp.modules.player.service;

import com.example.playersapp.modules.player.model.query.PlayerQueryParam;
import com.example.playersapp.modules.player.repository.PlayerConditionBuilder;
import com.example.playersapp.modules.player.repository.PlayerDbRepository;
import com.example.playersapp.modules.player.model.entity.PlayerEntity;
import com.example.playersapp.modules.player.service.validations.PlayerValidations;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerDbRepository playerRepository;

    @Autowired
    private PlayerValidations playerValidations;

    @Autowired
    private PlayerConditionBuilder playerConditionBuilder;


    public List<PlayerEntity> getPlayers(PlayerQueryParam playerQueryParam) throws BadRequestException {
        this.playerValidations.getPlayersValidation(playerQueryParam);

        Specification<PlayerEntity> spec = this.playerConditionBuilder.buildSpec(playerQueryParam);
        Optional<PageRequest> pageRequestOptional = this.playerConditionBuilder.getPage(playerQueryParam.getPage(), playerQueryParam.getSize());

        return pageRequestOptional
                .map(pageRequest -> playerRepository.findAll(spec, pageRequest).stream().toList())
                .orElseGet(() -> playerRepository.findAll(spec));
    }
}
