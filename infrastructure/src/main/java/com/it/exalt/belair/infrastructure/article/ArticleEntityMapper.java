package com.it.exalt.belair.infrastructure.article;

import com.it.exalt.belair.domain.article.Article;
import com.it.exalt.belair.domain.article.FoodType;
import org.springframework.stereotype.Component;

@Component
public class ArticleEntityMapper {

    public ArticleEntity toEntity(Article domain) {
        if (domain == null) return null;
        return new ArticleEntity(domain.id(), domain.nom(), domain.foodType().name());
    }

    public Article toDomain(ArticleEntity entity) {
        if (entity == null) return null;
        return new Article(entity.getId(), entity.getNom(), FoodType.valueOf(entity.getFoodType()));
    }
}
