package com.it.exalt.belair.infrastructure.article;

import com.it.exalt.belair.domain.article.Article;
import com.it.exalt.belair.domain.article.ArticleCatalogPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArticleCatalogRepositoryAdapter implements ArticleCatalogPort {

    private final ArticleJpaRepository jpaRepository;
    private final ArticleEntityMapper mapper;

    public ArticleCatalogRepositoryAdapter(ArticleJpaRepository jpaRepository, ArticleEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Article> findById(String id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    public void save(Article article) {
        jpaRepository.save(mapper.toEntity(article));
    }
}
