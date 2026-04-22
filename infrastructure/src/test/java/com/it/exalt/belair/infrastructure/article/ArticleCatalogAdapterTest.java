package com.it.exalt.belair.infrastructure.article;

import com.it.exalt.belair.domain.article.Article;
import com.it.exalt.belair.domain.article.FoodType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({ArticleCatalogRepositoryAdapter.class, ArticleEntityMapper.class})
class ArticleCatalogAdapterTest {

    @Autowired
    private ArticleCatalogRepositoryAdapter adapter;

    @Test
    void givenArticleSavedInCatalog_whenFindById_thenReturnsArticleWithCorrectProperties() {
        // Given
        Article article = new Article("cola-33cl", "Cola 33cl", FoodType.SNACK);
        adapter.save(article);

        // When
        Optional<Article> found = adapter.findById("cola-33cl");

        // Then
        assertTrue(found.isPresent());
        assertEquals("cola-33cl", found.get().id());
        assertEquals("Cola 33cl", found.get().nom());
        assertEquals(FoodType.SNACK, found.get().foodType());
    }

    @Test
    void givenEmptyCatalog_whenFindByUnknownId_thenReturnsEmpty() {
        // Given — no article saved

        // When
        Optional<Article> found = adapter.findById("unknown-article");

        // Then
        assertFalse(found.isPresent());
    }

    @Test
    void givenMealArticleSavedInCatalog_whenFindById_thenReturnsArticleWithMealFoodType() {
        // Given
        Article article = new Article("pasta-bolognese", "Pasta Bolognese", FoodType.MEAL);
        adapter.save(article);

        // When
        Optional<Article> found = adapter.findById("pasta-bolognese");

        // Then
        assertTrue(found.isPresent());
        assertEquals(FoodType.MEAL, found.get().foodType());
    }
}
