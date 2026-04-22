package com.it.exalt.belair.infrastructure.festivalier;

import com.it.exalt.belair.domain.festivalier.Festivalier;
import org.springframework.stereotype.Component;

@Component
public class FestivalierEntityMapper {

    public FestivalierEntity toEntity(Festivalier domain) {
        if (domain == null) return null;
        return new FestivalierEntity(domain.getId(), domain.getFoodTokenBalance());
    }

    public Festivalier toDomain(FestivalierEntity entity) {
        if (entity == null) return null;
        return new Festivalier(entity.getId(), entity.getFoodTokenBalance());
    }
}
