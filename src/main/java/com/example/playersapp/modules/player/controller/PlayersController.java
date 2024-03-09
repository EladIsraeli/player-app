package com.example.playersapp.modules.player.controller;

import com.example.playersapp.modules.player.model.entity.PlayerEntity;
import com.example.playersapp.modules.player.model.query.PlayerQueryParam;
import com.example.playersapp.modules.player.service.PlayerService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayersController {

    @Autowired
    private PlayerService playerService;

    @GetMapping
    public List<PlayerEntity> getPlayers(@Validated @ModelAttribute PlayerQueryParam playerQueryParam) throws BadRequestException {
        return this.playerService.getPlayers(playerQueryParam);
    }

    @GetMapping("/{id}")
    public List<PlayerEntity> getPlayer(@PathVariable String id) throws BadRequestException {
        PlayerQueryParam queryParam = new PlayerQueryParam(null, null , id, 0, 10);
        return this.playerService.getPlayers(queryParam);
    }
}
