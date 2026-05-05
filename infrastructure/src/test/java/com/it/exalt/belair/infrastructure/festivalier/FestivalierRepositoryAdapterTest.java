package com.it.exalt.belair.infrastructure.festivalier;

import com.it.exalt.belair.domain.festivalier.Festivalier;
import com.it.exalt.belair.domain.festivalier.TokenBalance;
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
        // Given
        Festivalier festivalier = new Festivalier("fest-1", new TokenBalance(10, 0));

        // When
        adapter.save(festivalier);

        // Then
        assertTrue(adapter.existsById("fest-1"));
    }

    @Test
    void givenNoFestivalier_whenCheckingExistsById_thenReturnsFalse() {
        // Given / When / Then
        assertFalse(adapter.existsById("non-existing-id"));
    }

    @Test
    void givenSavedFestivalier_whenFindById_thenReturnsFestivalierWithCorrectBalance() {
        // Given
        Festivalier festivalier = new Festivalier("fest-2", new TokenBalance(25, 3));
        adapter.save(festivalier);

        // When
        Optional<Festivalier> found = adapter.findById("fest-2");

        // Then
        assertTrue(found.isPresent());
        assertEquals("fest-2", found.get().getId());
        assertEquals(25, found.get().getFoodTokenBalance());
        assertEquals(3, found.get().getDrinkTokenBalance());
    }
}
