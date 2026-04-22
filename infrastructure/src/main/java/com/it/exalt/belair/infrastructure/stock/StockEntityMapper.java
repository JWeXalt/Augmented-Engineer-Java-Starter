package com.it.exalt.belair.infrastructure.stock;

import com.it.exalt.belair.domain.stock.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockEntityMapper {

    public StockEntity toEntity(Stock domain) {
        if (domain == null) return null;
        return new StockEntity(domain.articleId(), domain.quantite());
    }

    public Stock toDomain(StockEntity entity) {
        if (entity == null) return null;
        return new Stock(entity.getArticleId(), entity.getQuantite());
    }
}
