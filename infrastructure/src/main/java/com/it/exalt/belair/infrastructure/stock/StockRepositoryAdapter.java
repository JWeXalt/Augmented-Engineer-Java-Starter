package com.it.exalt.belair.infrastructure.stock;

import com.it.exalt.belair.domain.stock.Stock;
import com.it.exalt.belair.domain.stock.StockPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StockRepositoryAdapter implements StockPort {

    private final StockJpaRepository jpaRepository;
    private final StockEntityMapper mapper;

    public StockRepositoryAdapter(StockJpaRepository jpaRepository, StockEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Stock> findByArticleId(String articleId) {
        return jpaRepository.findById(articleId).map(mapper::toDomain);
    }

    @Override
    public void deductStock(String articleId, int quantite) {
        StockEntity entity = jpaRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found for article: " + articleId));
        entity.setQuantite(entity.getQuantite() - quantite);
        jpaRepository.save(entity);
    }

    public void save(Stock stock) {
        jpaRepository.save(mapper.toEntity(stock));
    }
}
