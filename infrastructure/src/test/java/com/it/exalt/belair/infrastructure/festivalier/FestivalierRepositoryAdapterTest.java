package com.it.exalt.belair.infrastructure.festivalier;

import com.it.exalt.belair.domain.festivalier.Festivalier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({FestivalierRepositoryAdapter.class, FestivalierEntityMapper.class})
class FestivalierRepositoryAdapterTest {

    @Autowired
    private FestivalierRepositoryAdapter adapter;

    @Test
    void givenFestivalierSaved_whenCheckingExistsById_thenReturnsTrue() {
        Festivalier festivalier = new Festivalier("fest-1", 10);
        adapter.save(festivalier);

        assertTrue(adapter.existsById("fest-1"));
    }

    @Test
    void givenNoFestivalier_whenCheckingExistsById_thenReturnsFalse() {
        assertFalse(adapter.existsById("non-existing-id"));
    }

    @Test
    void givenSavedFestivalier_whenFindById_thenReturnsFestivalierWithCorrectBalance() {
        Festivalier festivalier = new Festivalier("fest-2", 25);
        adapter.save(festivalier);

        Optional<Festivalier> found = adapter.findById("fest-2");
        assertTrue(found.isPresent());
        assertEquals("fest-2", found.get().getId());
        assertEquals(25, found.get().getFoodTokenBalance());
    }
}
