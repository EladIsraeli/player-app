package com.example.playersapp.modules.player.repository;

import com.example.playersapp.modules.player.model.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlayerDbRepository extends JpaRepository<PlayerEntity, Integer>, JpaSpecificationExecutor<PlayerEntity> {
}
