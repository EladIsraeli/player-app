package com.example.playersapp.modules.player.repository;

import com.example.playersapp.modules.player.model.query.PlayerQueryParam;
import com.example.playersapp.modules.player.model.entity.PlayerEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlayerConditionBuilder {

    public Specification<PlayerEntity> firstNameEquals(String firstName) {
        return (root, query, criteriaBuilder) -> {
            if (firstName == null || firstName.isEmpty()) {
                // If firstName is null or empty, no filtering is applied
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("nameFirst"), firstName);
        };
    }

    public Specification<PlayerEntity> lastNameEquals(String lastName) {
        return (root, query, criteriaBuilder) -> {
            if (lastName == null || lastName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("nameLast"), lastName);
        };
    }

    public Specification<PlayerEntity> playerIdEquals(String playerId) {
        return (root, query, criteriaBuilder) -> {
            if (playerId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("playerID"), playerId);
        };
    }

    public Optional<PageRequest> getPage(Integer page, Integer size) {
        if (page != null && size != null) {
            return Optional.of(PageRequest.of(page, size));
        }
        return Optional.empty();
    }

    public Specification<PlayerEntity> buildSpec(PlayerQueryParam playerQueryParam) {
        Specification<PlayerEntity> spec = Specification.where((this.firstNameEquals(playerQueryParam.getFirstName())));
        spec = spec.and(this.lastNameEquals(playerQueryParam.getLastName()));
        spec = spec.and(this.playerIdEquals(playerQueryParam.getPlayerID()));

        return spec;
    }
}