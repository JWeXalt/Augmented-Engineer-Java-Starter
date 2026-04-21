package com.it.exalt.belair.domain.stock;

import java.util.Optional;

public interface StockPort {

    Optional<Stock> findByArticleId(String articleId);

    void deductStock(String articleId, int quantite);
}
