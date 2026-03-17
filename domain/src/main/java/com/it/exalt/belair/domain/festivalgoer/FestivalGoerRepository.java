package com.it.exalt.belair.domain.festivalgoer;

import java.util.Optional;
import java.util.UUID;

/**
 * Port for retrieving festival goer data.
 */
public interface FestivalGoerRepository {
    Optional<FestivalGoer> findById(UUID id);
}
