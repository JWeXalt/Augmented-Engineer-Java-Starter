package com.it.exalt.belair.infrastructure.festivalier;

import com.it.exalt.belair.domain.festivalier.Festivalier;
import com.it.exalt.belair.domain.festivalier.FestivalierPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FestivalierRepositoryAdapter implements FestivalierPort {

    private final FestivalierJpaRepository jpaRepository;
    private final FestivalierEntityMapper mapper;

    public FestivalierRepositoryAdapter(FestivalierJpaRepository jpaRepository, FestivalierEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Festivalier> findById(String id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void save(Festivalier festivalier) {
        if (festivalier == null) return;
        jpaRepository.save(mapper.toEntity(festivalier));
    }

    @Override
    public boolean existsById(String id) {
        return jpaRepository.existsById(id);
    }
}
