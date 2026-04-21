package com.it.exalt.belair.domain.festivalier;

import java.util.Optional;

public interface FestivalierPort {

    Optional<Festivalier> findById(String id);
}
